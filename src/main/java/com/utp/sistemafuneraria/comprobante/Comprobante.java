package com.utp.sistemafuneraria.comprobante;

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
@Table(name = "Comprobante")
@Data
@NoArgsConstructor
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComprobante")
    private Integer idComprobante;

    @Column(name = "idServicio", nullable = false, unique = true)
    private Integer idServicio;

    @Column(name = "tipoComprobante")
    private String tipoComprobante;

    @Column(name = "numeroComprobante")
    private String numeroComprobante;

    @Column(name = "ruc")
    private String ruc;

    @Column(name = "razonSocial")
    private String razonSocial;

    @Column(name = "fechaEmision")
    private LocalDateTime fechaEmision;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "igv")
    private BigDecimal igv;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "estado")
    private String estado;

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
