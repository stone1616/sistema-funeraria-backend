package com.utp.sistemafuneraria.pago;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Integer> {

    List<Pago> findByFechaEliminacionIsNull();

    Optional<Pago> findByIdPagoAndFechaEliminacionIsNull(Integer idPago);

    List<Pago> findByIdComprobanteAndFechaEliminacionIsNull(Integer idComprobante);
}
