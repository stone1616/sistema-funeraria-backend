package com.utp.sistemafuneraria.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioFunerarioRepository extends JpaRepository<ServicioFunerario, Integer> {

    List<ServicioFunerario> findByFechaEliminacionIsNull();

    Optional<ServicioFunerario> findByIdServicioAndFechaEliminacionIsNull(Integer idServicio);

    Optional<ServicioFunerario> findByIdDifuntoAndFechaEliminacionIsNull(Integer idDifunto);

    boolean existsByIdDifuntoAndFechaEliminacionIsNull(Integer idDifunto);

    boolean existsByIdDifuntoAndIdServicioNotAndFechaEliminacionIsNull(Integer idDifunto, Integer idServicio);
}
