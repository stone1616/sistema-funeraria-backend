package com.utp.sistemafuneraria.difunto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PastOrPresent;

public class DifuntoDTO {

    record Request(
        @NotBlank(message = "El nombre es obligatorio")
        @Pattern(regexp = "^\\p{L}[\\p{L} '.\\-]*$", message = "El nombre solo permite letras y espacios")
        String nombres,

        @NotBlank(message = "El apellido es obligatorio")
        @Pattern(regexp = "^\\p{L}[\\p{L} '.\\-]*$", message = "El apellido solo permite letras y espacios")
        String apellidos,

        @Pattern(regexp = "^\\d{8,10}$", message = "El DNI debe contener solo números (8 a 10 dígitos)")
        String dni,

        @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
        LocalDate fechaNacimiento,

        @NotNull(message = "La fecha de defunción es obligatoria")
        @PastOrPresent(message = "La fecha de defunción no puede ser futura")
        LocalDate fechaDefuncion,

        String lugarDefuncion,

        String causaDefuncion,

        @NotNull(message = "El cliente es obligatorio")
        Integer idCliente
    ) {}

    record Response(
        Integer idDifunto,
        String nombres,
        String apellidos,
        String dni,
        LocalDate fechaNacimiento,
        LocalDate fechaDefuncion,
        String lugarDefuncion,
        String causaDefuncion,
        Integer idCliente,
        Boolean estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        LocalDateTime fechaEliminacion,
        Integer idUsuarioCreacion,
        Integer idUsuarioModificacion,
        Integer idUsuarioEliminacion
    ) {}

    record ListItem(
        Integer idDifunto,
        String nombres,
        String apellidos,
        String dni,
        LocalDate fechaNacimiento,
        LocalDate fechaDefuncion,
        String causaDefuncion,
        Integer idCliente,
        Boolean estado,
        LocalDateTime fechaCreacion
    ) {}
}
