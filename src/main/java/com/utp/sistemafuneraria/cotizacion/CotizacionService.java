package com.utp.sistemafuneraria.cotizacion;

import java.util.List;

import com.utp.sistemafuneraria.cotizacion.CotizacionDTO.ListItem;
import com.utp.sistemafuneraria.cotizacion.CotizacionDTO.Request;
import com.utp.sistemafuneraria.cotizacion.CotizacionDTO.Response;

public interface CotizacionService {

    List<ListItem> listar();

    Response obtenerPorId(Integer id);

    Response crear(Request request);

    Response actualizar(Integer id, Request request);

    void eliminar(Integer id);
}
