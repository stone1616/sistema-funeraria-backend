package com.utp.sistemafuneraria.movimiento;

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
@Table(name = "MovimientoInventario")
@Data
@NoArgsConstructor
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMovimiento")
    private Integer idMovimiento;

    @Column(name = "idProducto", nullable = false)
    private Integer idProducto;

    @Column(name = "tipoMovimiento")
    private String tipoMovimiento;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "idServicio")
    private Integer idServicio;

    @Column(name = "idPersonal")
    private Integer idPersonal;

    @Column(name = "proveedor")
    private String proveedor;

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
