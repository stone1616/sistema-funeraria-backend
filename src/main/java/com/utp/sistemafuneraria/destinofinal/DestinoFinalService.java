package com.utp.sistemafuneraria.destinofinal;

import java.util.List;

import com.utp.sistemafuneraria.destinofinal.DestinoFinalDTO.ListItem;
import com.utp.sistemafuneraria.destinofinal.DestinoFinalDTO.Request;
import com.utp.sistemafuneraria.destinofinal.DestinoFinalDTO.Response;

public interface DestinoFinalService {

    List<ListItem> listar();

    Response obtenerPorId(Integer id);

    Response crear(Request request);

    Response actualizar(Integer id, Request request);

    void eliminar(Integer id);
}
