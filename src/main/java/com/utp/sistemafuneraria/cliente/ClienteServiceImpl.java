package com.utp.sistemafuneraria.cliente;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.springframework.stereotype.Service;
import com.utp.sistemafuneraria.cliente.ClienteDTO.ListItem;
import com.utp.sistemafuneraria.cliente.ClienteDTO.Request;
import com.utp.sistemafuneraria.cliente.ClienteDTO.Response;
import com.utp.sistemafuneraria.shared.exception.DuplicateResourceException;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;
    private static final Integer USUARIO_ACTUAL = 1;
    
    @Override
    public List<ListItem> listar() {
        return clienteRepository.findByFechaEliminacionIsNull().stream()
                .map(this::toListItem)
                .toList();
    }

    @Override
    public Response obtenerPorId(Integer id) {
        Cliente cliente = clienteRepository.findByIdClienteAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con " + id + " no existe"));
        return toResponse(cliente);
    }
    
    @Override
    public Response buscarPorDni(String dni) {
    Cliente cliente = clienteRepository.findByDniAndFechaEliminacionIsNull(dni)
            .orElseThrow(() -> new ResourceNotFoundException("No existe un cliente activo con DNI " + dni));
    return toResponse(cliente);
}

    @Override
    public Response crear(Request request) {
        if (clienteRepository.existsByDniAndFechaEliminacionIsNull(request.dni())) {
            throw new DuplicateResourceException("Ya existe un cliente activo con el DNI " + request.dni());
        }

        Cliente cliente = new Cliente();
        cliente.setNombres(request.nombres());
        cliente.setApellidos(request.apellidos());
        cliente.setDni(request.dni());
        cliente.setTelefono(request.telefono());
        cliente.setEstado(true);
        cliente.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        cliente.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(clienteRepository.save(cliente));
    }

    @Override
    public Response actualizar(Integer id, Request request) {
        Cliente cliente = clienteRepository.findByIdClienteAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con id " + id + " no existe"));

        if (clienteRepository.existsByDniAndIdClienteNotAndFechaEliminacionIsNull(request.dni(), id)) {
            throw new DuplicateResourceException("Ya existe OTRO cliente activo con el DNI " + request.dni());
        }

        cliente.setNombres(request.nombres());
        cliente.setApellidos(request.apellidos());
        cliente.setDni(request.dni());
        cliente.setTelefono(request.telefono());
        cliente.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        cliente.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(clienteRepository.save(cliente));
    }

    @Override
    public void eliminar(Integer id) {
        Cliente cliente = clienteRepository.findByIdClienteAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con id " + id + " no existe"));

        cliente.setEstado(false);
        cliente.setFechaEliminacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        cliente.setIdUsuarioEliminacion(USUARIO_ACTUAL);

        clienteRepository.save(cliente);
    }

    private Response toResponse(Cliente c) {
        return new Response(
                c.getIdCliente(), c.getNombres(), c.getApellidos(), c.getDni(), c.getTelefono(),
                c.getFechaCreacion(), c.getFechaModificacion(), c.getFechaEliminacion(), c.getEstado(),
                c.getIdUsuarioCreacion(), c.getIdUsuarioModificacion(), c.getIdUsuarioEliminacion()
        );
    }

    private ListItem toListItem(Cliente c) {
        return new ListItem(
                c.getIdCliente(), c.getNombres(), c.getApellidos(), c.getDni(), c.getTelefono(),
                c.getFechaCreacion(), c.getEstado()
        );
    }
    
}
