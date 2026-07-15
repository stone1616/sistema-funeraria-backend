package com.utp.sistemafuneraria.pago;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.pago.PagoDTO.ListItem;
import com.utp.sistemafuneraria.pago.PagoDTO.Request;
import com.utp.sistemafuneraria.pago.PagoDTO.Response;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
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
        Pago pago = new Pago();
        pago.setIdComprobante(request.idComprobante());
        pago.setFechaPago(request.fechaPago());
        pago.setMonto(request.monto());
        pago.setMetodoPago(request.metodoPago());
        pago.setEstadoPago(request.estadoPago());
        pago.setNumeroRecibo(request.numeroRecibo());
        pago.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        pago.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(pagoRepository.save(pago));
    }

    @Override
    public Response actualizar(Integer id, Request request) {
        Pago pago = pagoRepository.findByIdPagoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago con id " + id + " no existe"));

        pago.setIdComprobante(request.idComprobante());
        pago.setFechaPago(request.fechaPago());
        pago.setMonto(request.monto());
        pago.setMetodoPago(request.metodoPago());
        pago.setEstadoPago(request.estadoPago());
        pago.setNumeroRecibo(request.numeroRecibo());
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

    private Response toResponse(Pago p) {
        return new Response(
                p.getIdPago(), p.getIdComprobante(), p.getFechaPago(), p.getMonto(), p.getMetodoPago(),
                p.getEstadoPago(), p.getNumeroRecibo(),
                p.getFechaCreacion(), p.getFechaModificacion(), p.getFechaEliminacion(),
                p.getIdUsuarioCreacion(), p.getIdUsuarioModificacion(), p.getIdUsuarioEliminacion()
        );
    }

    private ListItem toListItem(Pago p) {
        return new ListItem(p.getIdPago(), p.getIdComprobante(), p.getFechaPago(), p.getMonto(), p.getMetodoPago());
    }
}
