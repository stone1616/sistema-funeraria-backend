package com.utp.sistemafuneraria.recursos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public class VehiculoDTO {

    record Request(
        @NotBlank(message = "La placa es obligatoria")
        String placa,

        String marca,
        String modelo,
        Integer capacidad,

        @NotBlank(message = "El estado es obligatorio")
        String estado
    ) {}

    record Response(
        Integer idVehiculo,
        String placa,
        String marca,
        String modelo,
        Integer capacidad,
        String estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        LocalDateTime fechaEliminacion,
        Integer idUsuarioCreacion,
        Integer idUsuarioModificacion,
        Integer idUsuarioEliminacion
    ) {}

    record ListItem(
        Integer idVehiculo,
        String placa,
        String marca,
        String modelo,
        Integer capacidad,
        String estado
    ) {}
}
