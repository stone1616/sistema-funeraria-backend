package com.utp.sistemafuneraria.asignacionvehiculo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.asignacionvehiculo.AsignacionVehiculoDTO.ListItem;
import com.utp.sistemafuneraria.asignacionvehiculo.AsignacionVehiculoDTO.Request;
import com.utp.sistemafuneraria.asignacionvehiculo.AsignacionVehiculoDTO.Response;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsignacionVehiculoServiceImpl implements AsignacionVehiculoService {

    private final AsignacionVehiculoRepository asignacionRepository;
    private static final Integer USUARIO_ACTUAL = 1;

    @Override
    public List<ListItem> listar() {
        return asignacionRepository.findByFechaEliminacionIsNull().stream()
                .map(this::toListItem)
                .toList();
    }

    @Override
    public Response obtenerPorId(Integer id) {
        AsignacionVehiculo asignacion = asignacionRepository.findByIdAsignacionAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación con id " + id + " no existe"));
        return toResponse(asignacion);
    }

    @Override
    public Response crear(Request request) {
        AsignacionVehiculo asignacion = new AsignacionVehiculo();
        asignacion.setIdServicio(request.idServicio());
        asignacion.setIdVehiculo(request.idVehiculo());
        asignacion.setIdPersonal(request.idPersonal());
        asignacion.setFechaHora(request.fechaHora());
        asignacion.setEstado(request.estado());
        asignacion.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        asignacion.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(asignacionRepository.save(asignacion));
    }

    @Override
    public Response actualizar(Integer id, Request request) {
        AsignacionVehiculo asignacion = asignacionRepository.findByIdAsignacionAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación con id " + id + " no existe"));

        asignacion.setIdServicio(request.idServicio());
        asignacion.setIdVehiculo(request.idVehiculo());
        asignacion.setIdPersonal(request.idPersonal());
        asignacion.setFechaHora(request.fechaHora());
        asignacion.setEstado(request.estado());
        asignacion.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        asignacion.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(asignacionRepository.save(asignacion));
    }

    @Override
    public void eliminar(Integer id) {
        AsignacionVehiculo asignacion = asignacionRepository.findByIdAsignacionAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación con id " + id + " no existe"));

        asignacion.setFechaEliminacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        asignacion.setIdUsuarioEliminacion(USUARIO_ACTUAL);

        asignacionRepository.save(asignacion);
    }

    private Response toResponse(AsignacionVehiculo a) {
        return new Response(
                a.getIdAsignacion(), a.getIdServicio(), a.getIdVehiculo(), a.getIdPersonal(), a.getFechaHora(), a.getEstado(),
                a.getFechaCreacion(), a.getFechaModificacion(), a.getFechaEliminacion(),
                a.getIdUsuarioCreacion(), a.getIdUsuarioModificacion(), a.getIdUsuarioEliminacion()
        );
    }

    private ListItem toListItem(AsignacionVehiculo a) {
        return new ListItem(a.getIdAsignacion(), a.getIdServicio(), a.getIdVehiculo(), a.getIdPersonal(), a.getFechaHora(), a.getEstado());
    }
}
