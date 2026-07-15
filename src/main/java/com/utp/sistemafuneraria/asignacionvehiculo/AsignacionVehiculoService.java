package com.utp.sistemafuneraria.asignacionvehiculo;

import java.util.List;

import com.utp.sistemafuneraria.asignacionvehiculo.AsignacionVehiculoDTO.ListItem;
import com.utp.sistemafuneraria.asignacionvehiculo.AsignacionVehiculoDTO.Request;
import com.utp.sistemafuneraria.asignacionvehiculo.AsignacionVehiculoDTO.Response;

public interface AsignacionVehiculoService {

    List<ListItem> listar();

    Response obtenerPorId(Integer id);

    Response crear(Request request);

    Response actualizar(Integer id, Request request);

    void eliminar(Integer id);
}
