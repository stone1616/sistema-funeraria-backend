package com.utp.sistemafuneraria.recursos;

import java.util.List;

import com.utp.sistemafuneraria.recursos.VehiculoDTO.ListItem;
import com.utp.sistemafuneraria.recursos.VehiculoDTO.Request;
import com.utp.sistemafuneraria.recursos.VehiculoDTO.Response;

public interface VehiculoService {

    List<ListItem> listar();

    Response obtenerPorId(Integer id);

    Response crear(Request request);

    Response actualizar(Integer id, Request request);

    void eliminar(Integer id);
}
