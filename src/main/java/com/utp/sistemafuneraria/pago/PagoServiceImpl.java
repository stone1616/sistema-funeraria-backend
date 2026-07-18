package com.utp.sistemafuneraria.pago;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.comprobante.Comprobante;
import com.utp.sistemafuneraria.comprobante.ComprobanteRepository;
import com.utp.sistemafuneraria.pago.PagoDTO.ListItem;
import com.utp.sistemafuneraria.pago.PagoDTO.Request;
import com.utp.sistemafuneraria.pago.PagoDTO.Response;
import com.utp.sistemafuneraria.shared.exception.ApiException;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final ComprobanteRepository comprobanteRepository;
    private static final Integer USUARIO_ACTUAL = 1;

    @Override
    public List<ListItem> listar() {
        return pagoRepository.findByFechaEliminacionIsNull().stream()
                .map(this::toListItem)
                .toList();
    }

    @Override
    public Response obtenerPorId(Integer id) {
        Pago pago = pagoRepository.findByIdPagoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago con id " + id + " no existe"));
        return toResponse(pago);
    }

    @Override
    public Response crear(Request request) {
        validarPago(request, null);

        Pago pago = new Pago();
        pago.setIdComprobante(request.idComprobante());
        pago.setFechaPago(request.fechaPago() != null ? request.fechaPago() : LocalDateTime.now(ZoneId.of("America/Lima")));
        pago.setMonto(request.monto());
        pago.setMetodoPago(request.metodoPago());
        pago.setEstadoPago(request.estadoPago());
        pago.setNumeroRecibo(
                request.numeroRecibo() != null && !request.numeroRecibo().isBlank()
                        ? request.numeroRecibo().trim()
                        : generarNumeroRecibo());
        pago.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        pago.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(pagoRepository.save(pago));
    }

    @Override
    public Response actualizar(Integer id, Request request) {
        Pago pago = pagoRepository.findByIdPagoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago con id " + id + " no existe"));

        validarPago(request, id);

        pago.setIdComprobante(request.idComprobante());
        pago.setFechaPago(request.fechaPago());
        pago.setMonto(request.monto());
        pago.setMetodoPago(request.metodoPago());
        pago.setEstadoPago(request.estadoPago());
        pago.setNumeroRecibo(
                request.numeroRecibo() != null && !request.numeroRecibo().isBlank()
                        ? request.numeroRecibo().trim()
                        : (pago.getNumeroRecibo() != null && !pago.getNumeroRecibo().isBlank()
                                ? pago.getNumeroRecibo()
                                : generarNumeroRecibo()));
        pago.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        pago.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(pagoRepository.save(pago));
    }

    @Override
    public void eliminar(Integer id) {
        Pago pago = pagoRepository.findByIdPagoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago con id " + id + " no existe"));

        pago.setFechaEliminacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        pago.setIdUsuarioEliminacion(USUARIO_ACTUAL);

        pagoRepository.save(pago);
    }

    // Genera el siguiente correlativo de recibo: RE-000001, RE-000002, ...
    private String generarNumeroRecibo() {
        String prefijo = "RE-";
        int maximo = pagoRepository.findAll().stream()
                .map(Pago::getNumeroRecibo)
                .filter(n -> n != null && n.startsWith(prefijo))
                .map(n -> n.substring(prefijo.length()))
                .filter(s -> s.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
        return prefijo + String.format("%06d", maximo + 1);
    }

    private void validarPago(Request request, Integer idPagoActual) {
        Comprobante comprobante = comprobanteRepository
                .findByIdComprobanteAndFechaEliminacionIsNull(request.idComprobante())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comprobante con id " + request.idComprobante() + " no existe"));

        if ("ANULADA".equals(comprobante.getEstado())) {
            throw new ApiException("No se puede registrar un pago sobre un comprobante anulado.");
        }

        BigDecimal pagado = pagoRepository.findByIdComprobanteAndFechaEliminacionIsNull(request.idComprobante())
                .stream()
                .filter(p -> idPagoActual == null || !p.getIdPago().equals(idPagoActual))
                .map(p -> p.getMonto() != null ? p.getMonto() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal total = comprobante.getTotal() != null ? comprobante.getTotal() : BigDecimal.ZERO;
        BigDecimal saldo = total.subtract(pagado);

        if (request.monto().compareTo(saldo) > 0) {
            throw new ApiException("El monto excede el saldo pendiente del comprobante (S/ " + saldo + ").");
        }
    }

    private Response toResponse(Pago p) {
        return new Response(
                p.getIdPago(), p.getIdComprobante(), p.getFechaPago(), p.getMonto(), p.getMetodoPago(),
                p.getEstadoPago(), p.getNumeroRecibo(),
                p.getFechaCreacion(), p.getFechaModificacion(), p.getFechaEliminacion(),
                p.getIdUsuarioCreacion(), p.getIdUsuarioModificacion(), p.getIdUsuarioEliminacion()
        );
    }

    private ListItem toListItem(Pago p) {
        return new ListItem(p.getIdPago(), p.getIdComprobante(), p.getFechaPago(), p.getMonto(), p.getMetodoPago(), p.getNumeroRecibo());
    }
}
