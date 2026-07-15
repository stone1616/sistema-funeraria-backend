package com.utp.sistemafuneraria.destinofinal;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.destinofinal.DestinoFinalDTO.ListItem;
import com.utp.sistemafuneraria.destinofinal.DestinoFinalDTO.Request;
import com.utp.sistemafuneraria.destinofinal.DestinoFinalDTO.Response;
import com.utp.sistemafuneraria.shared.exception.DuplicateResourceException;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DestinoFinalServiceImpl implements DestinoFinalService {

    private final DestinoFinalRepository destinoFinalRepository;
    private static final Integer USUARIO_ACTUAL = 1;

    @Override
    public List<ListItem> listar() {
        return destinoFinalRepository.findByFechaEliminacionIsNull().stream()
                .map(this::toListItem)
                .toList();
    }

    @Override
    public Response obtenerPorId(Integer id) {
        DestinoFinal destino = destinoFinalRepository.findByIdDestinoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destino final con id " + id + " no existe"));
        return toResponse(destino);
    }

    @Override
    public Response crear(Request request) {
        if (destinoFinalRepository.existsByIdServicioAndFechaEliminacionIsNull(request.idServicio())) {
            throw new DuplicateResourceException("Este servicio ya tiene un destino final registrado");
        }

        DestinoFinal destino = new DestinoFinal();
        destino.setIdServicio(request.idServicio());
        destino.setTipo(request.tipo());
        destino.setCementerioCrematorio(request.cementerioCrematorio());
        destino.setPanteonLoteNicho(request.panteonLoteNicho());
        destino.setNumeroUrna(request.numeroUrna());
        destino.setFechaDestino(request.fechaDestino());
        destino.setEstado(request.estado());
        destino.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        destino.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(destinoFinalRepository.save(destino));
    }

    @Override
    public Response actualizar(Integer id, Request request) {
        DestinoFinal destino = destinoFinalRepository.findByIdDestinoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destino final con id " + id + " no existe"));

        if (destinoFinalRepository.existsByIdServicioAndIdDestinoNotAndFechaEliminacionIsNull(request.idServicio(), id)) {
            throw new DuplicateResourceException("Ese servicio ya tiene otro destino final registrado");
        }

        destino.setIdServicio(request.idServicio());
        destino.setTipo(request.tipo());
        destino.setCementerioCrematorio(request.cementerioCrematorio());
        destino.setPanteonLoteNicho(request.panteonLoteNicho());
        destino.setNumeroUrna(request.numeroUrna());
        destino.setFechaDestino(request.fechaDestino());
        destino.setEstado(request.estado());
        destino.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        destino.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(destinoFinalRepository.save(destino));
    }

    @Override
    public void eliminar(Integer id) {
        DestinoFinal destino = destinoFinalRepository.findByIdDestinoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destino final con id " + id + " no existe"));

        destino.setFechaEliminacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        destino.setIdUsuarioEliminacion(USUARIO_ACTUAL);

        destinoFinalRepository.save(destino);
    }

    private Response toResponse(DestinoFinal d) {
        return new Response(
                d.getIdDestino(), d.getIdServicio(), d.getTipo(), d.getCementerioCrematorio(),
                d.getPanteonLoteNicho(), d.getNumeroUrna(), d.getFechaDestino(), d.getEstado(),
                d.getFechaCreacion(), d.getFechaModificacion(), d.getFechaEliminacion(),
                d.getIdUsuarioCreacion(), d.getIdUsuarioModificacion(), d.getIdUsuarioEliminacion()
        );
    }

    private ListItem toListItem(DestinoFinal d) {
        return new ListItem(d.getIdDestino(), d.getIdServicio(), d.getTipo(), d.getCementerioCrematorio(), d.getFechaDestino(), d.getEstado());
    }
}
