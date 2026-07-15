package com.utp.sistemafuneraria.cotizacion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer> {

    List<Cotizacion> findByFechaEliminacionIsNull();

    Optional<Cotizacion> findByIdCotizacionAndFechaEliminacionIsNull(Integer idCotizacion);

    boolean existsByIdServicioAndFechaEliminacionIsNull(Integer idServicio);

    boolean existsByIdServicioAndIdCotizacionNotAndFechaEliminacionIsNull(Integer idServicio, Integer idCotizacion);
}
