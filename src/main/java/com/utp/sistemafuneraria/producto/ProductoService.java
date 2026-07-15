package com.utp.sistemafuneraria.producto;

import java.util.List;

import com.utp.sistemafuneraria.producto.ProductoDTO.ListItem;
import com.utp.sistemafuneraria.producto.ProductoDTO.Request;
import com.utp.sistemafuneraria.producto.ProductoDTO.Response;

public interface ProductoService {

    List<ListItem> listar();

    Response obtenerPorId(Integer id);

    Response crear(Request request);

    Response actualizar(Integer id, Request request);

    void eliminar(Integer id);
}
