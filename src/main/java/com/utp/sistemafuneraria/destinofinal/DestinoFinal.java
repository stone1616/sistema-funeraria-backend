package com.utp.sistemafuneraria.destinofinal;

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
@Table(name = "DestinoFinal")
@Data
@NoArgsConstructor
public class DestinoFinal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDestino")
    private Integer idDestino;

    @Column(name = "idServicio", nullable = false, unique = true)
    private Integer idServicio;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "cementerioCrematorio")
    private String cementerioCrematorio;

    @Column(name = "panteonLoteNicho")
    private String panteonLoteNicho;

    @Column(name = "numeroUrna")
    private String numeroUrna;

    @Column(name = "fechaDestino")
    private LocalDateTime fechaDestino;

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
