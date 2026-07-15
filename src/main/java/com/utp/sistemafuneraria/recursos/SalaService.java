package com.utp.sistemafuneraria.recursos;

import java.util.List;

import com.utp.sistemafuneraria.recursos.SalaDTO.ListItem;
import com.utp.sistemafuneraria.recursos.SalaDTO.Request;
import com.utp.sistemafuneraria.recursos.SalaDTO.Response;

public interface SalaService {

    List<ListItem> listar();

    Response obtenerPorId(Integer id);

    Response crear(Request request);

    Response actualizar(Integer id, Request request);

    void eliminar(Integer id);
}
