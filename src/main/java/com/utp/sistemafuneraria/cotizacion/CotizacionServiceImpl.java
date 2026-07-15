package com.utp.sistemafuneraria.cotizacion;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.cotizacion.CotizacionDTO.ListItem;
import com.utp.sistemafuneraria.cotizacion.CotizacionDTO.Request;
import com.utp.sistemafuneraria.cotizacion.CotizacionDTO.Response;
import com.utp.sistemafuneraria.shared.exception.DuplicateResourceException;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CotizacionServiceImpl implements CotizacionService {

    private final CotizacionRepository cotizacionRepository;
    private static final Integer USUARIO_ACTUAL = 1;

    @Override
    public List<ListItem> listar() {
        return cotizacionRepository.findByFechaEliminacionIsNull().stream()
                .map(this::toListItem)
                .toList();
    }

    @Override
    public Response obtenerPorId(Integer id) {
        Cotizacion cotizacion = cotizacionRepository.findByIdCotizacionAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cotización con id " + id + " no existe"));
        return toResponse(cotizacion);
    }

    @Override
    public Response crear(Request request) {
        if (cotizacionRepository.existsByIdServicioAndFechaEliminacionIsNull(request.idServicio())) {
            throw new DuplicateResourceException("Este servicio ya tiene una cotización registrada");
        }

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setIdServicio(request.idServicio());
        cotizacion.setMontoEstimado(request.montoEstimado());
        cotizacion.setFechaCotizacion(request.fechaCotizacion());
        cotizacion.setEstado(request.estado());
        cotizacion.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        cotizacion.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(cotizacionRepository.save(cotizacion));
    }

    @Override
    public Response actualizar(Integer id, Request request) {
        Cotizacion cotizacion = cotizacionRepository.findByIdCotizacionAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cotización con id " + id + " no existe"));

        if (cotizacionRepository.existsByIdServicioAndIdCotizacionNotAndFechaEliminacionIsNull(request.idServicio(), id)) {
            throw new DuplicateResourceException("Ese servicio ya tiene otra cotización registrada");
        }

        cotizacion.setIdServicio(request.idServicio());
        cotizacion.setMontoEstimado(request.montoEstimado());
        cotizacion.setFechaCotizacion(request.fechaCotizacion());
        cotizacion.setEstado(request.estado());
        cotizacion.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        cotizacion.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(cotizacionRepository.save(cotizacion));
    }

    @Override
    public void eliminar(Integer id) {
        Cotizacion cotizacion = cotizacionRepository.findByIdCotizacionAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cotización con id " + id + " no existe"));

        cotizacion.setFechaEliminacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        cotizacion.setIdUsuarioEliminacion(USUARIO_ACTUAL);

        cotizacionRepository.save(cotizacion);
    }

    private Response toResponse(Cotizacion c) {
        return new Response(
                c.getIdCotizacion(), c.getIdServicio(), c.getMontoEstimado(), c.getFechaCotizacion(), c.getEstado(),
                c.getFechaCreacion(), c.getFechaModificacion(), c.getFechaEliminacion(),
                c.getIdUsuarioCreacion(), c.getIdUsuarioModificacion(), c.getIdUsuarioEliminacion()
        );
    }

    private ListItem toListItem(Cotizacion c) {
        return new ListItem(c.getIdCotizacion(), c.getIdServicio(), c.getMontoEstimado(), c.getFechaCotizacion(), c.getEstado());
    }
}
