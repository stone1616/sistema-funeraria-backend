package com.utp.sistemafuneraria.movimiento;

import java.util.List;

import com.utp.sistemafuneraria.movimiento.MovimientoInventarioDTO.ListItem;
import com.utp.sistemafuneraria.movimiento.MovimientoInventarioDTO.Request;
import com.utp.sistemafuneraria.movimiento.MovimientoInventarioDTO.Response;

public interface MovimientoInventarioService {

    List<ListItem> listar();

    Response obtenerPorId(Integer id);

    Response crear(Request request);

    void eliminar(Integer id);
}
