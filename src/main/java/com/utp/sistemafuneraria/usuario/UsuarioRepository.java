package com.utp.sistemafuneraria.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByFechaEliminacionIsNull();

    Optional<Usuario> findByIdUsuarioAndFechaEliminacionIsNull(Integer idUsuario);

    Optional<Usuario> findByEmailAndFechaEliminacionIsNull(String email);

    boolean existsByEmailAndFechaEliminacionIsNull(String email);

    boolean existsByEmailAndIdUsuarioNotAndFechaEliminacionIsNull(String email, Integer idUsuario);
}
