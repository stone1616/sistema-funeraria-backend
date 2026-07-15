package com.utp.sistemafuneraria.producto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByFechaEliminacionIsNull();

    Optional<Producto> findByIdProductoAndFechaEliminacionIsNull(Integer idProducto);
}
