package com.utp.sistemafuneraria.asignacionvehiculo;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AsignacionVehiculoDTO {

    record Request(
        @NotNull(message = "El servicio es obligatorio")
        Integer idServicio,

        @NotNull(message = "El vehículo es obligatorio")
        Integer idVehiculo,

        Integer idPersonal,

        LocalDateTime fechaHora,

        @NotBlank(message = "El estado es obligatorio")
        String estado
    ) {}

    record Response(
        Integer idAsignacion,
        Integer idServicio,
        Integer idVehiculo,
        Integer idPersonal,
        LocalDateTime fechaHora,
        String estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        LocalDateTime fechaEliminacion,
        Integer idUsuarioCreacion,
        Integer idUsuarioModificacion,
        Integer idUsuarioEliminacion
    ) {}

    record ListItem(
        Integer idAsignacion,
        Integer idServicio,
        Integer idVehiculo,
        Integer idPersonal,
        LocalDateTime fechaHora,
        String estado
    ) {}
}
