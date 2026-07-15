package com.utp.sistemafuneraria.servicio;

import java.math.BigDecimal;
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
@Table(name = "ServicioFunerario")
@Data
@NoArgsConstructor
public class ServicioFunerario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idServicio")
    private Integer idServicio;

    @Column(name = "idCliente", nullable = false)
    private Integer idCliente;

    @Column(name = "idFallecido", nullable = false, unique = true)
    private Integer idDifunto;

    @Column(name = "parentescoCliente")
    private String parentescoCliente;

    @Column(name = "tipoServicio")
    private String tipoServicio;

    @Column(name = "fechaServicio")
    private LocalDateTime fechaServicio;

    @Column(name = "estado")
    private String estado;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "costoTotal")
    private BigDecimal costoTotal;

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
