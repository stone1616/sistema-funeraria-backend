package com.utp.sistemafuneraria.personal;

import jakarta.validation.constraints.NotBlank;

public class PersonalDTO {

    record ListItem(
        Integer idPersonal,
        String nombres,
        String apellidos,
        String dni,
        String cargo
    ) {}

    record CreateRequest(
        @NotBlank(message = "Los nombres son obligatorios")
        String nombres,

        @NotBlank(message = "Los apellidos son obligatorios")
        String apellidos,

        String dni,
        String telefono,
        String direccion,

        @NotBlank(message = "El cargo es obligatorio")
        String cargo
    ) {}
}
