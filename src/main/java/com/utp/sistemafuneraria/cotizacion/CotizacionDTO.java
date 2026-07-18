package com.utp.sistemafuneraria.cotizacion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class CotizacionDTO {

    record Request(
        @NotNull(message = "El servicio es obligatorio")
        Integer idServicio,

        @NotNull(message = "El monto estimado es obligatorio")
        @Positive(message = "El monto estimado debe ser mayor a 0")
        BigDecimal montoEstimado,

        LocalDateTime fechaCotizacion,

        @NotBlank(message = "El estado es obligatorio")
        @Pattern(regexp = "^(PENDIENTE|APROBADO|RECHAZADO)$", message = "Estado inválido")
        String estado
    ) {}

    record Response(
        Integer idCotizacion,
        Integer idServicio,
        BigDecimal montoEstimado,
        LocalDateTime fechaCotizacion,
        String estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        LocalDateTime fechaEliminacion,
        Integer idUsuarioCreacion,
        Integer idUsuarioModificacion,
        Integer idUsuarioEliminacion
    ) {}

    record ListItem(
        Integer idCotizacion,
        Integer idServicio,
        BigDecimal montoEstimado,
        LocalDateTime fechaCotizacion,
        String estado
    ) {}
}
