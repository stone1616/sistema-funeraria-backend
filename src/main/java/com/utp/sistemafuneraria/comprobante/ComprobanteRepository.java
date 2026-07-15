package com.utp.sistemafuneraria.comprobante;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComprobanteRepository extends JpaRepository<Comprobante, Integer> {

    List<Comprobante> findByFechaEliminacionIsNull();

    Optional<Comprobante> findByIdComprobanteAndFechaEliminacionIsNull(Integer idComprobante);

    boolean existsByIdServicioAndFechaEliminacionIsNull(Integer idServicio);

    boolean existsByIdServicioAndIdComprobanteNotAndFechaEliminacionIsNull(Integer idServicio, Integer idComprobante);
}
