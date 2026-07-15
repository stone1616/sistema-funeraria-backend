package com.utp.sistemafuneraria.recursos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface SalaDTO {

    record Request(
        @NotBlank(message = "El nombre de la sala es obligatorio")
        String nombreSala,

        @NotNull(message = "La capacidad es obligatoria")
        @Positive(message = "La capacidad debe ser mayor a 0")
        Integer capacidad,

        String ubicacion,

        @NotBlank(message = "El estado es obligatorio")
        String estado
    ) {}

    record Response(
        Integer idSala,
        String nombreSala,
        Integer capacidad,
        String ubicacion,
        String estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        LocalDateTime fechaEliminacion,
        Integer idUsuarioCreacion,
        Integer idUsuarioModificacion
    ) {}

    record ListItem(
        Integer idSala,
        String nombreSala,
        Integer capacidad,
        String ubicacion,
        String estado
    ) {}
}
