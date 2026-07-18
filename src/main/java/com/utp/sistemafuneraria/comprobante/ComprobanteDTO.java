package com.utp.sistemafuneraria.comprobante;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class ComprobanteDTO {

    record Request(
        @NotNull(message = "El servicio es obligatorio")
        Integer idServicio,

        @NotBlank(message = "El tipo de comprobante es obligatorio")
        @Pattern(regexp = "^(BOLETA|FACTURA)$", message = "El tipo debe ser BOLETA o FACTURA")
        String tipoComprobante,

        String numeroComprobante,

        @Pattern(regexp = "^\\d{11}$", message = "El RUC debe tener 11 dígitos")
        String ruc,
        String razonSocial,

        LocalDateTime fechaEmision,

        @NotNull(message = "El subtotal es obligatorio")
        @Positive(message = "El subtotal debe ser mayor a 0")
        BigDecimal subtotal,

        BigDecimal igv,

        @NotNull(message = "El total es obligatorio")
        BigDecimal total,

        @NotBlank(message = "El estado es obligatorio")
        @Pattern(regexp = "^(EMITIDA|ANULADA)$", message = "Estado inválido")
        String estado
    ) {}

    record Response(
        Integer idComprobante,
        Integer idServicio,
        String tipoComprobante,
        String numeroComprobante,
        String ruc,
        String razonSocial,
        LocalDateTime fechaEmision,
        BigDecimal subtotal,
        BigDecimal igv,
        BigDecimal total,
        String estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        LocalDateTime fechaEliminacion,
        Integer idUsuarioCreacion,
        Integer idUsuarioModificacion,
        Integer idUsuarioEliminacion
    ) {}

    record ListItem(
        Integer idComprobante,
        Integer idServicio,
        String tipoComprobante,
        String numeroComprobante,
        String ruc,
        String razonSocial,
        LocalDateTime fechaEmision,
        BigDecimal subtotal,
        BigDecimal igv,
        BigDecimal total,
        String estado
    ) {}
}
