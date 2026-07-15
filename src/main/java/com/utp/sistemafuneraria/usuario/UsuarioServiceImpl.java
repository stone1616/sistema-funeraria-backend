package com.utp.sistemafuneraria.usuario;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.personal.Personal;
import com.utp.sistemafuneraria.personal.PersonalRepository;
import com.utp.sistemafuneraria.shared.exception.DuplicateResourceException;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;
import com.utp.sistemafuneraria.usuario.UsuarioDTO.CreateRequest;
import com.utp.sistemafuneraria.usuario.UsuarioDTO.ListItem;
import com.utp.sistemafuneraria.usuario.UsuarioDTO.Response;
import com.utp.sistemafuneraria.usuario.UsuarioDTO.UpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonalRepository personalRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Integer USUARIO_ACTUAL = 1;

    @Override
    public List<ListItem> listar() {
        Map<Integer, Personal> personalPorId = personalRepository.findAll().stream()
                .collect(java.util.stream.Collectors.toMap(Personal::getIdPersonal, Function.identity()));

        return usuarioRepository.findByFechaEliminacionIsNull().stream()
                .map(u -> toListItem(u, personalPorId.get(u.getIdPersonal())))
                .toList();
    }

    @Override
    public Response obtenerPorId(Integer id) {
        Usuario usuario = usuarioRepository.findByIdUsuarioAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + id + " no existe"));
        return toResponse(usuario);
    }

    @Override
    public Response crear(CreateRequest request) {
        if (usuarioRepository.existsByEmailAndFechaEliminacionIsNull(request.email())) {
            throw new DuplicateResourceException("Ya existe un usuario activo con el email " + request.email());
        }

        Usuario usuario = new Usuario();
        usuario.setIdPersonal(request.idPersonal());
        usuario.setEmail(request.email());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setRol(request.rol());
        usuario.setEstado(true);
        usuario.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        usuario.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public Response actualizar(Integer id, UpdateRequest request) {
        Usuario usuario = usuarioRepository.findByIdUsuarioAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + id + " no existe"));

        if (usuarioRepository.existsByEmailAndIdUsuarioNotAndFechaEliminacionIsNull(request.email(), id)) {
            throw new DuplicateResourceException("Ya existe otro usuario activo con el email " + request.email());
        }

        usuario.setIdPersonal(request.idPersonal());
        usuario.setEmail(request.email());
        usuario.setRol(request.rol());
        usuario.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        usuario.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public Response cambiarEstado(Integer id, Boolean estado) {
        Usuario usuario = usuarioRepository.findByIdUsuarioAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + id + " no existe"));

        usuario.setEstado(estado);
        usuario.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        usuario.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public void eliminar(Integer id) {
        Usuario usuario = usuarioRepository.findByIdUsuarioAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + id + " no existe"));

        usuario.setEstado(false);
        usuario.setFechaEliminacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        usuario.setIdUsuarioEliminacion(USUARIO_ACTUAL);

        usuarioRepository.save(usuario);
    }

    private Response toResponse(Usuario u) {
        return new Response(
                u.getIdUsuario(), u.getIdPersonal(), u.getEmail(), u.getRol(), u.getEstado(),
                u.getFechaCreacion(), u.getFechaModificacion(), u.getFechaEliminacion(),
                u.getIdUsuarioCreacion(), u.getIdUsuarioModificacion(), u.getIdUsuarioEliminacion()
        );
    }

    private ListItem toListItem(Usuario u, Personal p) {
        return new ListItem(
                u.getIdUsuario(), u.getIdPersonal(),
                p != null ? p.getNombres() : null,
                p != null ? p.getApellidos() : null,
                p != null ? p.getCargo() : null,
                u.getEmail(), u.getRol(), u.getEstado(),
                u.getFechaCreacion()
        );
    }
}
