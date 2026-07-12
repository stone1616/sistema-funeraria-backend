package com.utp.sistemafuneraria.recursos;

import java.time.LocalDateTime;
import java.util.List;

public interface SalaDTO {

    record Request(
        String nombreSala,
        Integer capacidad,
        String ubicacion,
        String estado,
        String disponibilidad
    ) {}

    record Response(
        Integer idSala,
        String nombreSala,
        Integer capacidad,
        String ubicacion,
        String estado,
        String disponibilidad,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        LocalDateTime fechaEliminacion,
        Integer idEmpleadoCreador,
        Integer idEmpleadoModificador
    ) {}

    record ListItem(
        Integer idSala,
        String nombreSala,
        Integer capacidad,
        String estado,
        String disponibilidad
    ) {}

    record ListResponse(List<ListItem> items) {}

}
