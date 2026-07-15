package com.utp.sistemafuneraria.servicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ServicioFunerarioDTO {

    record Request(
        @NotNull(message = "El difunto es obligatorio")
        Integer idDifunto,

        String parentescoCliente,

        @NotBlank(message = "El tipo de servicio es obligatorio")
        String tipoServicio,

        @NotNull(message = "La fecha de servicio es obligatoria")
        LocalDateTime fechaServicio,

        @NotBlank(message = "El estado es obligatorio")
        @Pattern(regexp = "^(EN_VELACION|CREMACION_PROGRAMADA|INHUMADO)$", message = "Estado inválido")
        String estado,

        String descripcion,

        @DecimalMin(value = "0", inclusive = true, message = "El costo no puede ser negativo")
        BigDecimal costoTotal
    ) {}

    record Response(
        Integer idServicio,
        Integer idCliente,
        Integer idDifunto,
        String parentescoCliente,
        String tipoServicio,
        LocalDateTime fechaServicio,
        String estado,
        String descripcion,
        BigDecimal costoTotal,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        LocalDateTime fechaEliminacion,
        Integer idUsuarioCreacion,
        Integer idUsuarioModificacion,
        Integer idUsuarioEliminacion
    ) {}

    record ListItem(
        Integer idServicio,
        Integer idCliente,
        Integer idDifunto,
        String tipoServicio,
        LocalDateTime fechaServicio,
        String estado,
        BigDecimal costoTotal
    ) {}
}
