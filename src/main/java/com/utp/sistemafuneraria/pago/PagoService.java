package com.utp.sistemafuneraria.pago;

import java.util.List;

import com.utp.sistemafuneraria.pago.PagoDTO.ListItem;
import com.utp.sistemafuneraria.pago.PagoDTO.Request;
import com.utp.sistemafuneraria.pago.PagoDTO.Response;

public interface PagoService {

    List<ListItem> listar();

    Response obtenerPorId(Integer id);

    Response crear(Request request);

    Response actualizar(Integer id, Request request);

    void eliminar(Integer id);
}
