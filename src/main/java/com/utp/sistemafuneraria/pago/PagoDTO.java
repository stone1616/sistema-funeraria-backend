package com.utp.sistemafuneraria.pago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class PagoDTO {

    record Request(
        @NotNull(message = "El comprobante es obligatorio")
        Integer idComprobante,

        LocalDateTime fechaPago,

        @NotNull(message = "El monto es obligatorio")
        @Positive(message = "El monto debe ser mayor a 0")
        BigDecimal monto,

        @NotBlank(message = "El método de pago es obligatorio")
        @Pattern(regexp = "^(EFECTIVO|TARJETA|TRANSFERENCIA|YAPE|PLIN)$", message = "Método de pago inválido")
        String metodoPago,

        @Pattern(regexp = "^(PENDIENTE|CONFIRMADO|ANULADO)$", message = "Estado de pago inválido")
        String estadoPago,
        String numeroRecibo
    ) {}

    record Response(
        Integer idPago,
        Integer idComprobante,
        LocalDateTime fechaPago,
        BigDecimal monto,
        String metodoPago,
        String estadoPago,
        String numeroRecibo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        LocalDateTime fechaEliminacion,
        Integer idUsuarioCreacion,
        Integer idUsuarioModificacion,
        Integer idUsuarioEliminacion
    ) {}

    record ListItem(
        Integer idPago,
        Integer idComprobante,
        LocalDateTime fechaPago,
        BigDecimal monto,
        String metodoPago,
        String numeroRecibo
    ) {}
}
