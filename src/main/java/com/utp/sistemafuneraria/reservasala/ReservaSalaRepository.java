package com.utp.sistemafuneraria.reservasala;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaSalaRepository extends JpaRepository<ReservaSala, Integer> {

    List<ReservaSala> findByFechaEliminacionIsNull();

    Optional<ReservaSala> findByIdReservaAndFechaEliminacionIsNull(Integer idReserva);

    Optional<ReservaSala> findByIdServicioAndFechaEliminacionIsNull(Integer idServicio);

    boolean existsByIdServicioAndFechaEliminacionIsNull(Integer idServicio);

    boolean existsByIdServicioAndIdReservaNotAndFechaEliminacionIsNull(Integer idServicio, Integer idReserva);
}
