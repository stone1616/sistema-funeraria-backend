package com.utp.sistemafuneraria.reservasala;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ReservaSalaDTO {

    record Request(
        @NotNull(message = "El servicio es obligatorio")
        Integer idServicio,

        @NotNull(message = "La sala es obligatoria")
        Integer idSala,

        @NotNull(message = "La fecha de inicio es obligatoria")
        LocalDateTime fechaInicio,

        LocalDateTime fechaFin,

        @NotBlank(message = "El estado es obligatorio")
        @Pattern(regexp = "^(RESERVADA|EN_CURSO|FINALIZADA)$", message = "Estado inválido")
        String estado
    ) {}

    record Response(
        Integer idReserva,
        Integer idServicio,
        Integer idSala,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        String estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        LocalDateTime fechaEliminacion,
        Integer idUsuarioCreacion,
        Integer idUsuarioModificacion,
        Integer idUsuarioEliminacion
    ) {}

    record ListItem(
        Integer idReserva,
        Integer idServicio,
        Integer idSala,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        String estado
    ) {}
}
