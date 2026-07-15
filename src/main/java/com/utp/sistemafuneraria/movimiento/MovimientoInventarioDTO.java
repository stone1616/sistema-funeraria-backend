package com.utp.sistemafuneraria.movimiento;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class MovimientoInventarioDTO {

    record Request(
        @NotNull(message = "El producto es obligatorio")
        Integer idProducto,

        @NotBlank(message = "El tipo de movimiento es obligatorio")
        @Pattern(regexp = "^(ENTRADA|SALIDA)$", message = "El tipo debe ser ENTRADA o SALIDA")
        String tipoMovimiento,

        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad debe ser mayor a 0")
        Integer cantidad,

        LocalDateTime fecha,

        Integer idServicio,
        Integer idPersonal,
        String proveedor
    ) {}

    record Response(
        Integer idMovimiento,
        Integer idProducto,
        String tipoMovimiento,
        Integer cantidad,
        LocalDateTime fecha,
        Integer idServicio,
        Integer idPersonal,
        String proveedor,
        LocalDateTime fechaCreacion,
        Integer idUsuarioCreacion
    ) {}

    record ListItem(
        Integer idMovimiento,
        Integer idProducto,
        String tipoMovimiento,
        Integer cantidad,
        LocalDateTime fecha,
        Integer idServicio,
        Integer idPersonal,
        String proveedor
    ) {}
}
