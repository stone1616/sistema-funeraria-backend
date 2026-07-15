package com.utp.sistemafuneraria.movimiento;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utp.sistemafuneraria.movimiento.MovimientoInventarioDTO.ListItem;
import com.utp.sistemafuneraria.movimiento.MovimientoInventarioDTO.Request;
import com.utp.sistemafuneraria.movimiento.MovimientoInventarioDTO.Response;
import com.utp.sistemafuneraria.producto.Producto;
import com.utp.sistemafuneraria.producto.ProductoRepository;
import com.utp.sistemafuneraria.shared.exception.ApiException;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    private final MovimientoInventarioRepository movimientoRepository;
    private final ProductoRepository productoRepository;
    private static final Integer USUARIO_ACTUAL = 1;

    @Override
    public List<ListItem> listar() {
        return movimientoRepository.findByFechaEliminacionIsNull().stream()
                .map(this::toListItem)
                .toList();
    }

    @Override
    public Response obtenerPorId(Integer id) {
        MovimientoInventario movimiento = movimientoRepository.findByIdMovimientoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento con id " + id + " no existe"));
        return toResponse(movimiento);
    }

    @Override
    @Transactional
    public Response crear(Request request) {
        Producto producto = productoRepository.findByIdProductoAndFechaEliminacionIsNull(request.idProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto con id " + request.idProducto() + " no existe"));

        int stockActual = producto.getStock() != null ? producto.getStock() : 0;

        if ("SALIDA".equals(request.tipoMovimiento())) {
            if (stockActual < request.cantidad()) {
                throw new ApiException("No hay stock suficiente. Stock actual: " + stockActual);
            }
            producto.setStock(stockActual - request.cantidad());
        } else {
            producto.setStock(stockActual + request.cantidad());
        }
        productoRepository.save(producto);

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setIdProducto(request.idProducto());
        movimiento.setTipoMovimiento(request.tipoMovimiento());
        movimiento.setCantidad(request.cantidad());
        movimiento.setFecha(request.fecha() != null ? request.fecha() : LocalDateTime.now(ZoneId.of("America/Lima")));
        movimiento.setIdServicio(request.idServicio());
        movimiento.setIdPersonal(request.idPersonal());
        movimiento.setProveedor(request.proveedor());
        movimiento.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        movimiento.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(movimientoRepository.save(movimiento));
    }

    @Override
    public void eliminar(Integer id) {
        MovimientoInventario movimiento = movimientoRepository.findByIdMovimientoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento con id " + id + " no existe"));

        movimiento.setFechaEliminacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        movimiento.setIdUsuarioEliminacion(USUARIO_ACTUAL);

        movimientoRepository.save(movimiento);
    }

    private Response toResponse(MovimientoInventario m) {
        return new Response(
                m.getIdMovimiento(), m.getIdProducto(), m.getTipoMovimiento(), m.getCantidad(), m.getFecha(),
                m.getIdServicio(), m.getIdPersonal(), m.getProveedor(),
                m.getFechaCreacion(), m.getIdUsuarioCreacion()
        );
    }

    private ListItem toListItem(MovimientoInventario m) {
        return new ListItem(
                m.getIdMovimiento(), m.getIdProducto(), m.getTipoMovimiento(), m.getCantidad(), m.getFecha(),
                m.getIdServicio(), m.getIdPersonal(), m.getProveedor()
        );
    }
}
