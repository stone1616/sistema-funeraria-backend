package com.utp.sistemafuneraria.recursos;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.recursos.SalaDTO.ListItem;
import com.utp.sistemafuneraria.recursos.SalaDTO.Request;
import com.utp.sistemafuneraria.recursos.SalaDTO.Response;
import com.utp.sistemafuneraria.recursos.dao.SalaDAO;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalaServiceImpl implements SalaService {

    private final SalaDAO salaDAO;
    private static final Integer USUARIO_ACTUAL = 1;

    @Override
    public List<ListItem> listar() {
        return salaDAO.listar().stream().map(this::toListItem).toList();
    }

    @Override
    public Response obtenerPorId(Integer id) {
        Sala sala = salaDAO.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala con id " + id + " no existe"));
        return toResponse(sala);
    }

    @Override
    public Response crear(Request request) {
        Sala sala = new Sala();
        sala.setNombreSala(request.nombreSala());
        sala.setCapacidad(request.capacidad());
        sala.setUbicacion(request.ubicacion());
        sala.setEstado(request.estado());
        sala.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        sala.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(salaDAO.insertar(sala));
    }

    @Override
    public Response actualizar(Integer id, Request request) {
        Sala sala = salaDAO.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala con id " + id + " no existe"));

        sala.setNombreSala(request.nombreSala());
        sala.setCapacidad(request.capacidad());
        sala.setUbicacion(request.ubicacion());
        sala.setEstado(request.estado());
        sala.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        sala.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(salaDAO.actualizar(sala));
    }

    @Override
    public void eliminar(Integer id) {
        salaDAO.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala con id " + id + " no existe"));
        salaDAO.eliminar(id);
    }

    private Response toResponse(Sala s) {
        return new Response(
                s.getIdSala(), s.getNombreSala(), s.getCapacidad(), s.getUbicacion(), s.getEstado(),
                s.getFechaCreacion(), s.getFechaModificacion(), s.getFechaEliminacion(),
                s.getIdUsuarioCreacion(), s.getIdUsuarioModificacion()
        );
    }

    private ListItem toListItem(Sala s) {
        return new ListItem(s.getIdSala(), s.getNombreSala(), s.getCapacidad(), s.getUbicacion(), s.getEstado());
    }
}
