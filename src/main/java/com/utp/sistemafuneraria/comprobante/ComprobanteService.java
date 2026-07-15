package com.utp.sistemafuneraria.comprobante;

import java.util.List;

import com.utp.sistemafuneraria.comprobante.ComprobanteDTO.ListItem;
import com.utp.sistemafuneraria.comprobante.ComprobanteDTO.Request;
import com.utp.sistemafuneraria.comprobante.ComprobanteDTO.Response;

public interface ComprobanteService {

    List<ListItem> listar();

    Response obtenerPorId(Integer id);

    Response crear(Request request);

    Response actualizar(Integer id, Request request);

    void eliminar(Integer id);
}
