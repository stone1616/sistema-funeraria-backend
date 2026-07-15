package com.utp.sistemafuneraria.producto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.producto.ProductoDTO.ListItem;
import com.utp.sistemafuneraria.producto.ProductoDTO.Request;
import com.utp.sistemafuneraria.producto.ProductoDTO.Response;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private static final Integer USUARIO_ACTUAL = 1;

    @Override
    public List<ListItem> listar() {
        return productoRepository.findByFechaEliminacionIsNull().stream()
                .map(this::toListItem)
                .toList();
    }

    @Override
    public Response obtenerPorId(Integer id) {
        Producto producto = productoRepository.findByIdProductoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con id " + id + " no existe"));
        return toResponse(producto);
    }

    @Override
    public Response crear(Request request) {
        Producto producto = new Producto();
        producto.setNombre(request.nombre());
        producto.setCategoria(request.categoria());
        producto.setMaterial(request.material());
        producto.setColor(request.color());
        producto.setPrecio(request.precio());
        producto.setStock(request.stock());
        producto.setEstado(request.estado());
        producto.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        producto.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(productoRepository.save(producto));
    }

    @Override
    public Response actualizar(Integer id, Request request) {
        Producto producto = productoRepository.findByIdProductoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con id " + id + " no existe"));

        producto.setNombre(request.nombre());
        producto.setCategoria(request.categoria());
        producto.setMaterial(request.material());
        producto.setColor(request.color());
        producto.setPrecio(request.precio());
        producto.setStock(request.stock());
        producto.setEstado(request.estado());
        producto.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        producto.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(productoRepository.save(producto));
    }

    @Override
    public void eliminar(Integer id) {
        Producto producto = productoRepository.findByIdProductoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con id " + id + " no existe"));

        producto.setFechaEliminacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        producto.setIdUsuarioEliminacion(USUARIO_ACTUAL);

        productoRepository.save(producto);
    }

    private Response toResponse(Producto p) {
        return new Response(
                p.getIdProducto(), p.getNombre(), p.getCategoria(), p.getMaterial(), p.getColor(),
                p.getPrecio(), p.getStock(), p.getEstado(),
                p.getFechaCreacion(), p.getFechaModificacion(), p.getFechaEliminacion(),
                p.getIdUsuarioCreacion(), p.getIdUsuarioModificacion(), p.getIdUsuarioEliminacion()
        );
    }

    private ListItem toListItem(Producto p) {
        return new ListItem(p.getIdProducto(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock(), p.getEstado());
    }
}
