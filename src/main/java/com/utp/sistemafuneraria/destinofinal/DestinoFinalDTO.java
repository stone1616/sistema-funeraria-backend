package com.utp.sistemafuneraria.destinofinal;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class DestinoFinalDTO {

    record Request(
        @NotNull(message = "El servicio es obligatorio")
        Integer idServicio,

        @NotBlank(message = "El tipo es obligatorio")
        @Pattern(regexp = "^(INHUMACION|CREMACION)$", message = "El tipo debe ser INHUMACION o CREMACION")
        String tipo,

        String cementerioCrematorio,
        String panteonLoteNicho,
        String numeroUrna,
        LocalDateTime fechaDestino,

        @NotBlank(message = "El estado es obligatorio")
        String estado
    ) {}

    record Response(
        Integer idDestino,
        Integer idServicio,
        String tipo,
        String cementerioCrematorio,
        String panteonLoteNicho,
        String numeroUrna,
        LocalDateTime fechaDestino,
        String estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        LocalDateTime fechaEliminacion,
        Integer idUsuarioCreacion,
        Integer idUsuarioModificacion,
        Integer idUsuarioEliminacion
    ) {}

    record ListItem(
        Integer idDestino,
        Integer idServicio,
        String tipo,
        String cementerioCrematorio,
        LocalDateTime fechaDestino,
        String estado
    ) {}
}
