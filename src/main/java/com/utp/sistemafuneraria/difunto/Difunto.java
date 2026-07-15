package com.utp.sistemafuneraria.difunto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "difunto")
@Data
@NoArgsConstructor
public class Difunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_difunto")
    private Integer idDifunto;

    @Column(name = "nombres", nullable = false)
    private String nombres;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "dni")
    private String dni;

    @Column(name = "fechaNacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "fechaDefuncion", nullable = false)
    private LocalDate fechaDefuncion;

    @Column(name = "lugarDefuncion")
    private String lugarDefuncion;

    @Column(name = "causaDefuncion")
    private String causaDefuncion;

    @Column(name = "idCliente", nullable = false)
    private Integer idCliente;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "fechaCreacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fechaModificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "fechaEliminacion")
    private LocalDateTime fechaEliminacion;

    @Column(name = "idUsuarioCreacion", nullable = false)
    private Integer idUsuarioCreacion;

    @Column(name = "idUsuarioModificacion")
    private Integer idUsuarioModificacion;

    @Column(name = "idUsuarioEliminacion")
    private Integer idUsuarioEliminacion;
}
