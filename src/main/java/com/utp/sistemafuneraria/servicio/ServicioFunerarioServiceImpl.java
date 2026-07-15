package com.utp.sistemafuneraria.servicio;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.difunto.DifuntoRepository;
import com.utp.sistemafuneraria.servicio.ServicioFunerarioDTO.ListItem;
import com.utp.sistemafuneraria.servicio.ServicioFunerarioDTO.Request;
import com.utp.sistemafuneraria.servicio.ServicioFunerarioDTO.Response;
import com.utp.sistemafuneraria.shared.exception.DuplicateResourceException;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioFunerarioServiceImpl implements ServicioFunerarioService {

    private final ServicioFunerarioRepository servicioRepository;
    private final DifuntoRepository difuntoRepository;
    private static final Integer USUARIO_ACTUAL = 1;

    @Override
    public List<ListItem> listar() {
        return servicioRepository.findByFechaEliminacionIsNull().stream()
                .map(this::toListItem)
                .toList();
    }

    @Override
    public Response obtenerPorId(Integer id) {
        ServicioFunerario servicio = servicioRepository.findByIdServicioAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio con id " + id + " no existe"));
        return toResponse(servicio);
    }

    @Override
    public Optional<Response> obtenerPorDifunto(Integer idDifunto) {
        return servicioRepository.findByIdDifuntoAndFechaEliminacionIsNull(idDifunto).map(this::toResponse);
    }

    @Override
    public Response crear(Request request) {
        var difunto = difuntoRepository.findByIdDifuntoAndFechaEliminacionIsNull(request.idDifunto())
                .orElseThrow(() -> new ResourceNotFoundException("Difunto con id " + request.idDifunto() + " no existe"));

        if (servicioRepository.existsByIdDifuntoAndFechaEliminacionIsNull(request.idDifunto())) {
            throw new DuplicateResourceException("Este difunto ya tiene un servicio funerario registrado");
        }

        ServicioFunerario servicio = new ServicioFunerario();
        servicio.setIdDifunto(request.idDifunto());
        servicio.setIdCliente(difunto.getIdCliente());
        servicio.setParentescoCliente(request.parentescoCliente());
        servicio.setTipoServicio(request.tipoServicio());
        servicio.setFechaServicio(request.fechaServicio());
        servicio.setEstado(request.estado());
        servicio.setDescripcion(request.descripcion());
        servicio.setCostoTotal(request.costoTotal());
        servicio.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        servicio.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(servicioRepository.save(servicio));
    }

    @Override
    public Response actualizar(Integer id, Request request) {
        ServicioFunerario servicio = servicioRepository.findByIdServicioAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio con id " + id + " no existe"));

        if (servicioRepository.existsByIdDifuntoAndIdServicioNotAndFechaEliminacionIsNull(request.idDifunto(), id)) {
            throw new DuplicateResourceException("Ese difunto ya tiene otro servicio funerario registrado");
        }

        var difunto = difuntoRepository.findByIdDifuntoAndFechaEliminacionIsNull(request.idDifunto())
                .orElseThrow(() -> new ResourceNotFoundException("Difunto con id " + request.idDifunto() + " no existe"));

        servicio.setIdDifunto(request.idDifunto());
        servicio.setIdCliente(difunto.getIdCliente());
        servicio.setParentescoCliente(request.parentescoCliente());
        servicio.setTipoServicio(request.tipoServicio());
        servicio.setFechaServicio(request.fechaServicio());
        servicio.setEstado(request.estado());
        servicio.setDescripcion(request.descripcion());
        servicio.setCostoTotal(request.costoTotal());
        servicio.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        servicio.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(servicioRepository.save(servicio));
    }

    @Override
    public void eliminar(Integer id) {
        ServicioFunerario servicio = servicioRepository.findByIdServicioAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio con id " + id + " no existe"));

        servicio.setFechaEliminacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        servicio.setIdUsuarioEliminacion(USUARIO_ACTUAL);

        servicioRepository.save(servicio);
    }

    private Response toResponse(ServicioFunerario s) {
        return new Response(
                s.getIdServicio(), s.getIdCliente(), s.getIdDifunto(), s.getParentescoCliente(),
                s.getTipoServicio(), s.getFechaServicio(), s.getEstado(), s.getDescripcion(), s.getCostoTotal(),
                s.getFechaCreacion(), s.getFechaModificacion(), s.getFechaEliminacion(),
                s.getIdUsuarioCreacion(), s.getIdUsuarioModificacion(), s.getIdUsuarioEliminacion()
        );
    }

    private ListItem toListItem(ServicioFunerario s) {
        return new ListItem(
                s.getIdServicio(), s.getIdCliente(), s.getIdDifunto(),
                s.getTipoServicio(), s.getFechaServicio(), s.getEstado(), s.getCostoTotal()
        );
    }
}
