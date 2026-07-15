package com.utp.sistemafuneraria.asignacionvehiculo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignacionVehiculoRepository extends JpaRepository<AsignacionVehiculo, Integer> {

    List<AsignacionVehiculo> findByFechaEliminacionIsNull();

    Optional<AsignacionVehiculo> findByIdAsignacionAndFechaEliminacionIsNull(Integer idAsignacion);

    List<AsignacionVehiculo> findByIdVehiculoAndFechaEliminacionIsNullOrderByFechaHoraDesc(Integer idVehiculo);
}
