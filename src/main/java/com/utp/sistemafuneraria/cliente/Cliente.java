package com.utp.sistemafuneraria.cliente;
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
@Table(name = "cliente")
@Data
@NoArgsConstructor

public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;
    
    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "dni")
    private String dni;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "fechaCreacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fechaModificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "fechaEliminacion")
    private LocalDateTime fechaEliminacion;

    @Column(name = "idEmpleadoCreador", nullable = false)
    private Integer idEmpleadoCreador;

    @Column(name = "idEmpleadoModificador")
    private Integer idEmpleadoModificador;

    @Column(name = "idEmpleadoEliminador")
    private Integer idEmpleadoEliminador;
}
