package com.utp.sistemafuneraria.comprobante;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.comprobante.ComprobanteDTO.ListItem;
import com.utp.sistemafuneraria.comprobante.ComprobanteDTO.Request;
import com.utp.sistemafuneraria.comprobante.ComprobanteDTO.Response;
import com.utp.sistemafuneraria.pago.PagoRepository;
import com.utp.sistemafuneraria.shared.exception.ApiException;
import com.utp.sistemafuneraria.shared.exception.DuplicateResourceException;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComprobanteServiceImpl implements ComprobanteService {

    private final ComprobanteRepository comprobanteRepository;
    private final PagoRepository pagoRepository;
    private static final Integer USUARIO_ACTUAL = 1;
    private static final BigDecimal TASA_IGV = new BigDecimal("0.18");

    @Override
    public List<ListItem> listar() {
        return comprobanteRepository.findByFechaEliminacionIsNull().stream()
                .map(this::toListItem)
                .toList();
    }

    @Override
    public Response obtenerPorId(Integer id) {
        Comprobante comprobante = comprobanteRepository.findByIdComprobanteAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comprobante con id " + id + " no existe"));
        return toResponse(comprobante);
    }

    @Override
    public Response crear(Request request) {
        if (comprobanteRepository.existsByIdServicioAndFechaEliminacionIsNull(request.idServicio())) {
            throw new DuplicateResourceException("Este servicio ya tiene un comprobante registrado");
        }

        validarDatosFactura(request);

        Comprobante comprobante = new Comprobante();
        aplicarDatos(comprobante, request);
        comprobante.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        comprobante.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(comprobanteRepository.save(comprobante));
    }

    @Override
    public Response actualizar(Integer id, Request request) {
        Comprobante comprobante = comprobanteRepository.findByIdComprobanteAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comprobante con id " + id + " no existe"));

        if (comprobanteRepository.existsByIdServicioAndIdComprobanteNotAndFechaEliminacionIsNull(request.idServicio(), id)) {
            throw new DuplicateResourceException("Ese servicio ya tiene otro comprobante registrado");
        }

        validarDatosFactura(request);

        BigDecimal pagado = pagoRepository.findByIdComprobanteAndFechaEliminacionIsNull(id).stream()
                .map(p -> p.getMonto() != null ? p.getMonto() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (calcularTotal(request.subtotal()).compareTo(pagado) < 0) {
            throw new ApiException("El nuevo total no puede ser menor que lo ya pagado (S/ " + pagado + ").");
        }

        aplicarDatos(comprobante, request);
        comprobante.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        comprobante.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(comprobanteRepository.save(comprobante));
    }

    // El IGV y el total siempre se recalculan en el servidor a partir del
    // subtotal, para que un cliente no pueda enviar montos inconsistentes.
    private BigDecimal calcularIgv(BigDecimal subtotal) {
        return subtotal.multiply(TASA_IGV).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularTotal(BigDecimal subtotal) {
        return subtotal.add(calcularIgv(subtotal)).setScale(2, RoundingMode.HALF_UP);
    }

    // Genera el siguiente correlativo por serie: BOLETA -> B001-000001, FACTURA -> F001-000001.
    private String generarNumeroComprobante(String tipoComprobante) {
        String serie = "FACTURA".equals(tipoComprobante) ? "F001" : "B001";
        String prefijo = serie + "-";
        int maximo = comprobanteRepository.findAll().stream()
                .map(Comprobante::getNumeroComprobante)
                .filter(n -> n != null && n.startsWith(prefijo))
                .map(n -> n.substring(prefijo.length()))
                .filter(s -> s.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
        return prefijo + String.format("%06d", maximo + 1);
    }

    private void validarDatosFactura(Request request) {
        if ("FACTURA".equals(request.tipoComprobante())) {
            if (request.ruc() == null || request.ruc().isBlank()) {
                throw new ApiException("Una factura requiere el RUC del cliente.");
            }
            if (request.razonSocial() == null || request.razonSocial().isBlank()) {
                throw new ApiException("Una factura requiere la razón social del cliente.");
            }
        }
    }

    private void aplicarDatos(Comprobante comprobante, Request request) {
        comprobante.setIdServicio(request.idServicio());
        comprobante.setTipoComprobante(request.tipoComprobante());
        // Si el usuario no escribe un número, se genera uno correlativo automático.
        String numero = request.numeroComprobante() != null && !request.numeroComprobante().isBlank()
                ? request.numeroComprobante().trim()
                : (comprobante.getNumeroComprobante() != null && !comprobante.getNumeroComprobante().isBlank()
                        ? comprobante.getNumeroComprobante()
                        : generarNumeroComprobante(request.tipoComprobante()));
        comprobante.setNumeroComprobante(numero);
        comprobante.setRuc(request.ruc());
        comprobante.setRazonSocial(request.razonSocial());
        comprobante.setFechaEmision(
                request.fechaEmision() != null ? request.fechaEmision() : LocalDateTime.now(ZoneId.of("America/Lima")));
        comprobante.setSubtotal(request.subtotal());
        comprobante.setIgv(calcularIgv(request.subtotal()));
        comprobante.setTotal(calcularTotal(request.subtotal()));
        comprobante.setEstado(request.estado());
    }

    @Override
    public void eliminar(Integer id) {
        Comprobante comprobante = comprobanteRepository.findByIdComprobanteAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comprobante con id " + id + " no existe"));

        comprobante.setFechaEliminacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        comprobante.setIdUsuarioEliminacion(USUARIO_ACTUAL);

        comprobanteRepository.save(comprobante);
    }

    private Response toResponse(Comprobante c) {
        return new Response(
                c.getIdComprobante(), c.getIdServicio(), c.getTipoComprobante(), c.getNumeroComprobante(),
                c.getRuc(), c.getRazonSocial(), c.getFechaEmision(), c.getSubtotal(), c.getIgv(), c.getTotal(), c.getEstado(),
                c.getFechaCreacion(), c.getFechaModificacion(), c.getFechaEliminacion(),
                c.getIdUsuarioCreacion(), c.getIdUsuarioModificacion(), c.getIdUsuarioEliminacion()
        );
    }

    private ListItem toListItem(Comprobante c) {
        return new ListItem(
                c.getIdComprobante(), c.getIdServicio(), c.getTipoComprobante(), c.getNumeroComprobante(),
                c.getRuc(), c.getRazonSocial(), c.getFechaEmision(), c.getSubtotal(), c.getIgv(), c.getTotal(), c.getEstado()
        );
    }
}
