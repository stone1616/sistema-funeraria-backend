package com.utp.sistemafuneraria.recursos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    List<Vehiculo> findByFechaEliminacionIsNull();

    Optional<Vehiculo> findByIdVehiculoAndFechaEliminacionIsNull(Integer idVehiculo);
}
