package com.utp.sistemafuneraria.reservasala;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.reservasala.ReservaSalaDTO.ListItem;
import com.utp.sistemafuneraria.reservasala.ReservaSalaDTO.Request;
import com.utp.sistemafuneraria.reservasala.ReservaSalaDTO.Response;
import com.utp.sistemafuneraria.shared.exception.DuplicateResourceException;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaSalaServiceImpl implements ReservaSalaService {

    private final ReservaSalaRepository reservaSalaRepository;
    private static final Integer USUARIO_ACTUAL = 1;

    @Override
    public List<ListItem> listar() {
        return reservaSalaRepository.findByFechaEliminacionIsNull().stream()
                .map(this::toListItem)
                .toList();
    }

    @Override
    public Response obtenerPorId(Integer id) {
        ReservaSala reserva = reservaSalaRepository.findByIdReservaAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva con id " + id + " no existe"));
        return toResponse(reserva);
    }

    @Override
    public Response crear(Request request) {
        if (reservaSalaRepository.existsByIdServicioAndFechaEliminacionIsNull(request.idServicio())) {
            throw new DuplicateResourceException("Este servicio ya tiene una reserva de sala registrada");
        }

        ReservaSala reserva = new ReservaSala();
        reserva.setIdServicio(request.idServicio());
        reserva.setIdSala(request.idSala());
        reserva.setFechaInicio(request.fechaInicio());
        reserva.setFechaFin(request.fechaFin());
        reserva.setEstado(request.estado());
        reserva.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        reserva.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(reservaSalaRepository.save(reserva));
    }

    @Override
    public Response actualizar(Integer id, Request request) {
        ReservaSala reserva = reservaSalaRepository.findByIdReservaAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva con id " + id + " no existe"));

        if (reservaSalaRepository.existsByIdServicioAndIdReservaNotAndFechaEliminacionIsNull(request.idServicio(), id)) {
            throw new DuplicateResourceException("Ese servicio ya tiene otra reserva de sala registrada");
        }

        reserva.setIdServicio(request.idServicio());
        reserva.setIdSala(request.idSala());
        reserva.setFechaInicio(request.fechaInicio());
        reserva.setFechaFin(request.fechaFin());
        reserva.setEstado(request.estado());
        reserva.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        reserva.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(reservaSalaRepository.save(reserva));
    }

    @Override
    public void eliminar(Integer id) {
        ReservaSala reserva = reservaSalaRepository.findByIdReservaAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva con id " + id + " no existe"));

        reserva.setFechaEliminacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        reserva.setIdUsuarioEliminacion(USUARIO_ACTUAL);

        reservaSalaRepository.save(reserva);
    }

    private Response toResponse(ReservaSala r) {
        return new Response(
                r.getIdReserva(), r.getIdServicio(), r.getIdSala(), r.getFechaInicio(), r.getFechaFin(), r.getEstado(),
                r.getFechaCreacion(), r.getFechaModificacion(), r.getFechaEliminacion(),
                r.getIdUsuarioCreacion(), r.getIdUsuarioModificacion(), r.getIdUsuarioEliminacion()
        );
    }

    private ListItem toListItem(ReservaSala r) {
        return new ListItem(r.getIdReserva(), r.getIdServicio(), r.getIdSala(), r.getFechaInicio(), r.getFechaFin(), r.getEstado());
    }
}
