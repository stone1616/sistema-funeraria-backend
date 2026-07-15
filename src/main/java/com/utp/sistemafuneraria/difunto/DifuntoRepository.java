package com.utp.sistemafuneraria.difunto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DifuntoRepository extends JpaRepository<Difunto, Integer> {

    List<Difunto> findByFechaEliminacionIsNull();

    List<Difunto> findByIdClienteAndFechaEliminacionIsNull(Integer idCliente);

    Optional<Difunto> findByIdDifuntoAndFechaEliminacionIsNull(Integer idDifunto);

    boolean existsByDniAndFechaEliminacionIsNull(String dni);

    boolean existsByDniAndIdDifuntoNotAndFechaEliminacionIsNull(String dni, Integer idDifunto);
}
