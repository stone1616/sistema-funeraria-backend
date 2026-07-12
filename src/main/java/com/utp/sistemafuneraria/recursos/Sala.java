package com.utp.sistemafuneraria.recursos;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Sala {

    private int idSala;
    private String nombreSala;
    private Integer capacidad;
    private String ubicacion;
    private String estado;
    private String disponibilidad;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private LocalDateTime fechaEliminacion;
    private Integer idEmpleadoCreador;
    private Integer idEmpleadoModificador;
    private Integer idEmpleadoEliminador;
    
}
