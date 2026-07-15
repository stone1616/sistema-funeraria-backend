package com.utp.sistemafuneraria.comprobante;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.comprobante.ComprobanteDTO.ListItem;
import com.utp.sistemafuneraria.comprobante.ComprobanteDTO.Request;
import com.utp.sistemafuneraria.comprobante.ComprobanteDTO.Response;
import com.utp.sistemafuneraria.shared.exception.DuplicateResourceException;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComprobanteServiceImpl implements ComprobanteService {

    private final ComprobanteRepository comprobanteRepository;
    private static final Integer USUARIO_ACTUAL = 1;

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

        Comprobante comprobante = new Comprobante();
        comprobante.setIdServicio(request.idServicio());
        comprobante.setTipoComprobante(request.tipoComprobante());
        comprobante.setNumeroComprobante(request.numeroComprobante());
        comprobante.setRuc(request.ruc());
        comprobante.setRazonSocial(request.razonSocial());
        comprobante.setFechaEmision(request.fechaEmision());
        comprobante.setSubtotal(request.subtotal());
        comprobante.setIgv(request.igv());
        comprobante.setTotal(request.total());
        comprobante.setEstado(request.estado());
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

        comprobante.setIdServicio(request.idServicio());
        comprobante.setTipoComprobante(request.tipoComprobante());
        comprobante.setNumeroComprobante(request.numeroComprobante());
        comprobante.setRuc(request.ruc());
        comprobante.setRazonSocial(request.razonSocial());
        comprobante.setFechaEmision(request.fechaEmision());
        comprobante.setSubtotal(request.subtotal());
        comprobante.setIgv(request.igv());
        comprobante.setTotal(request.total());
        comprobante.setEstado(request.estado());
        comprobante.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        comprobante.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(comprobanteRepository.save(comprobante));
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
                c.getFechaEmision(), c.getIgv(), c.getTotal(), c.getEstado()
        );
    }
}
