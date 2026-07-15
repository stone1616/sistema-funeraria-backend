package com.utp.sistemafuneraria.cliente;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.*;


public class ClienteDTO {
    
    record Request(
        @NotBlank(message = "El nombre es obligatorio")
        @Pattern(regexp = "^\\p{L}[\\p{L} '.\\-]*$", message = "El nombre solo permite letras y espacios")
        String nombres,

        @NotBlank(message = "El apellido es obligatorio")
        @Pattern(regexp = "^\\p{L}[\\p{L} '.\\-]*$", message = "El apellido solo permite letras y espacios")
        String apellidos,

        @NotBlank(message = "El DNI es obligatorio")
        @Pattern(regexp = "^\\d{8,10}$", message = "El DNI debe contener solo números (8 a 10 dígitos)")
        String dni,

        @Pattern(regexp = "^\\d{7,15}$", message = "El teléfono debe contener solo números (7 a 15 dígitos)")
        String telefono
    ) {}

    record Response(
        Integer idCliente,
        String nombres,
        String apellidos,
        String dni,
        String telefono,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        LocalDateTime fechaEliminacion,
        Boolean estado,
        Integer idUsuarioCreacion,
        Integer idUsuarioModificacion,
        Integer idUsuarioEliminacion
    ) {}

    record ListItem(
        Integer idCliente,
        String nombres,
        String apellidos,
        String dni,
        String telefono,
        LocalDateTime fechaCreacion,
        Boolean estado
    ) {}

    record ListResponse(List<ListItem> items) {}
}
