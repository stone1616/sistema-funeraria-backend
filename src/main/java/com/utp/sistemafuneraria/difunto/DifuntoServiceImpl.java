package com.utp.sistemafuneraria.difunto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.cliente.ClienteRepository;
import com.utp.sistemafuneraria.difunto.DifuntoDTO.ListItem;
import com.utp.sistemafuneraria.difunto.DifuntoDTO.Request;
import com.utp.sistemafuneraria.difunto.DifuntoDTO.Response;
import com.utp.sistemafuneraria.shared.exception.ApiException;
import com.utp.sistemafuneraria.shared.exception.DuplicateResourceException;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DifuntoServiceImpl implements DifuntoService {

    private final DifuntoRepository difuntoRepository;
    private final ClienteRepository clienteRepository;
    private static final Integer USUARIO_ACTUAL = 1;

    @Override
    public List<ListItem> listar() {
        return difuntoRepository.findByFechaEliminacionIsNull().stream()
                .map(this::toListItem)
                .toList();
    }

    @Override
    public List<ListItem> listarPorCliente(Integer idCliente) {
        return difuntoRepository.findByIdClienteAndFechaEliminacionIsNull(idCliente).stream()
                .map(this::toListItem)
                .toList();
    }

    @Override
    public Response obtenerPorId(Integer id) {
        Difunto difunto = difuntoRepository.findByIdDifuntoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Difunto con id " + id + " no existe"));
        return toResponse(difunto);
    }

    @Override
    public Response crear(Request request) {
        clienteRepository.findByIdClienteAndFechaEliminacionIsNull(request.idCliente())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con id " + request.idCliente() + " no existe"));

        if (request.dni() != null && difuntoRepository.existsByDniAndFechaEliminacionIsNull(request.dni())) {
            throw new DuplicateResourceException("Ya existe un difunto registrado con el DNI " + request.dni());
        }

        if (request.fechaNacimiento() != null && request.fechaNacimiento().isAfter(request.fechaDefuncion())) {
            throw new ApiException("La fecha de nacimiento no puede ser posterior a la fecha de defunción");
        }

        Difunto difunto = new Difunto();
        difunto.setNombres(request.nombres());
        difunto.setApellidos(request.apellidos());
        difunto.setDni(request.dni());
        difunto.setFechaNacimiento(request.fechaNacimiento());
        difunto.setFechaDefuncion(request.fechaDefuncion());
        difunto.setLugarDefuncion(request.lugarDefuncion());
        difunto.setCausaDefuncion(request.causaDefuncion());
        difunto.setIdCliente(request.idCliente());
        difunto.setEstado(true);
        difunto.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        difunto.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(difuntoRepository.save(difunto));
    }

    @Override
    public Response actualizar(Integer id, Request request) {
        Difunto difunto = difuntoRepository.findByIdDifuntoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Difunto con id " + id + " no existe"));

        if (request.dni() != null
                && difuntoRepository.existsByDniAndIdDifuntoNotAndFechaEliminacionIsNull(request.dni(), id)) {
            throw new DuplicateResourceException("Ya existe otro difunto registrado con el DNI " + request.dni());
        }

        if (request.fechaNacimiento() != null && request.fechaNacimiento().isAfter(request.fechaDefuncion())) {
            throw new ApiException("La fecha de nacimiento no puede ser posterior a la fecha de defunción");
        }

        difunto.setNombres(request.nombres());
        difunto.setApellidos(request.apellidos());
        difunto.setDni(request.dni());
        difunto.setFechaNacimiento(request.fechaNacimiento());
        difunto.setFechaDefuncion(request.fechaDefuncion());
        difunto.setLugarDefuncion(request.lugarDefuncion());
        difunto.setCausaDefuncion(request.causaDefuncion());
        difunto.setIdCliente(request.idCliente());
        difunto.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        difunto.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(difuntoRepository.save(difunto));
    }

    @Override
    public void eliminar(Integer id) {
        Difunto difunto = difuntoRepository.findByIdDifuntoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Difunto con id " + id + " no existe"));

        difunto.setEstado(false);
        difunto.setFechaEliminacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        difunto.setIdUsuarioEliminacion(USUARIO_ACTUAL);

        difuntoRepository.save(difunto);
    }

    private Response toResponse(Difunto d) {
        return new Response(
                d.getIdDifunto(), d.getNombres(), d.getApellidos(), d.getDni(),
                d.getFechaNacimiento(), d.getFechaDefuncion(), d.getLugarDefuncion(),
                d.getCausaDefuncion(), d.getIdCliente(), d.getEstado(),
                d.getFechaCreacion(), d.getFechaModificacion(), d.getFechaEliminacion(),
                d.getIdUsuarioCreacion(), d.getIdUsuarioModificacion(), d.getIdUsuarioEliminacion()
        );
    }

    private ListItem toListItem(Difunto d) {
        return new ListItem(
                d.getIdDifunto(), d.getNombres(), d.getApellidos(), d.getDni(),
                d.getFechaNacimiento(), d.getFechaDefuncion(), d.getCausaDefuncion(),
                d.getIdCliente(), d.getEstado(), d.getFechaCreacion()
        );
    }
}
