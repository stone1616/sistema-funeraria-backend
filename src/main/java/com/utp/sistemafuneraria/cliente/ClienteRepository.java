package com.utp.sistemafuneraria.cliente;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

 // Lista los clientes que no han sido eliminados
    List<Cliente> findByFechaEliminacionIsNull();

    // Busca un cliente por ID solo si no ha sido eliminado
    Optional<Cliente> findByIdClienteAndFechaEliminacionIsNull(Integer idCliente);

    // Busca un cliente activo por su DNI exacto (para el flujo "buscar antes de crear")
    Optional<Cliente> findByDniAndFechaEliminacionIsNull(String dni);

    // Para validar que el DNI no se repita entre clientes activos, al crear uno nuevo
    boolean existsByDniAndFechaEliminacionIsNull(String dni);

    // Verifica si OTRO cliente activo ya tiene ese DNI, excluyendo al propio cliente
    boolean existsByDniAndIdClienteNotAndFechaEliminacionIsNull(String dni, Integer idCliente);

}
