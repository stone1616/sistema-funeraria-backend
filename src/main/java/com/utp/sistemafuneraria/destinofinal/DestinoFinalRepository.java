package com.utp.sistemafuneraria.destinofinal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinoFinalRepository extends JpaRepository<DestinoFinal, Integer> {

    List<DestinoFinal> findByFechaEliminacionIsNull();

    Optional<DestinoFinal> findByIdDestinoAndFechaEliminacionIsNull(Integer idDestino);

    Optional<DestinoFinal> findByIdServicioAndFechaEliminacionIsNull(Integer idServicio);

    boolean existsByIdServicioAndFechaEliminacionIsNull(Integer idServicio);

    boolean existsByIdServicioAndIdDestinoNotAndFechaEliminacionIsNull(Integer idServicio, Integer idDestino);
}
