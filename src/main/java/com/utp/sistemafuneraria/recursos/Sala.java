package com.utp.sistemafuneraria.recursos;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Sala {

    private Integer idSala;
    private String nombreSala;
    private Integer capacidad;
    private String ubicacion;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private LocalDateTime fechaEliminacion;
    private Integer idUsuarioCreacion;
    private Integer idUsuarioModificacion;
    private Integer idUsuarioEliminacion;

}
