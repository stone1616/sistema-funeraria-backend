package com.utp.sistemafuneraria.movimiento;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Integer> {

    List<MovimientoInventario> findByFechaEliminacionIsNull();

    Optional<MovimientoInventario> findByIdMovimientoAndFechaEliminacionIsNull(Integer idMovimiento);
}
