package com.utp.sistemafuneraria.usuario;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {

    record CreateRequest(
        @NotNull(message = "El personal es obligatorio")
        Integer idPersonal,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,

        @NotBlank(message = "El rol es obligatorio")
        @Pattern(regexp = "^(ADMIN|EMPLEADO)$", message = "El rol debe ser ADMIN o EMPLEADO")
        String rol
    ) {}

    record UpdateRequest(
        @NotNull(message = "El personal es obligatorio")
        Integer idPersonal,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        String email,

        @NotBlank(message = "El rol es obligatorio")
        @Pattern(regexp = "^(ADMIN|EMPLEADO)$", message = "El rol debe ser ADMIN o EMPLEADO")
        String rol
    ) {}

    record Response(
        Integer idUsuario,
        Integer idPersonal,
        String email,
        String rol,
        Boolean estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        LocalDateTime fechaEliminacion,
        Integer idUsuarioCreacion,
        Integer idUsuarioModificacion,
        Integer idUsuarioEliminacion
    ) {}

    record ListItem(
        Integer idUsuario,
        Integer idPersonal,
        String nombres,
        String apellidos,
        String cargo,
        String email,
        String rol,
        Boolean estado,
        LocalDateTime fechaCreacion
    ) {}

    record EstadoRequest(
        @NotNull(message = "El estado es obligatorio")
        Boolean estado
    ) {}
}
