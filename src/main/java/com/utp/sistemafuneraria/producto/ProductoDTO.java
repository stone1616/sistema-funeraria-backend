package com.utp.sistemafuneraria.producto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductoDTO {

    record Request(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "La categoría es obligatoria")
        String categoria,

        String material,
        String color,

        @NotNull(message = "El precio es obligatorio")
        @PositiveOrZero(message = "El precio no puede ser negativo")
        BigDecimal precio,

        @NotNull(message = "El stock inicial es obligatorio")
        @PositiveOrZero(message = "El stock no puede ser negativo")
        Integer stock,

        @NotBlank(message = "El estado es obligatorio")
        String estado
    ) {}

    record Response(
        Integer idProducto,
        String nombre,
        String categoria,
        String material,
        String color,
        BigDecimal precio,
        Integer stock,
        String estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        LocalDateTime fechaEliminacion,
        Integer idUsuarioCreacion,
        Integer idUsuarioModificacion,
        Integer idUsuarioEliminacion
    ) {}

    record ListItem(
        Integer idProducto,
        String nombre,
        String categoria,
        BigDecimal precio,
        Integer stock,
        String estado
    ) {}
}
