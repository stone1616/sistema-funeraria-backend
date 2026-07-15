package com.utp.sistemafuneraria.difunto;

import java.util.List;

import com.utp.sistemafuneraria.difunto.DifuntoDTO.ListItem;
import com.utp.sistemafuneraria.difunto.DifuntoDTO.Request;
import com.utp.sistemafuneraria.difunto.DifuntoDTO.Response;

public interface DifuntoService {
    List<ListItem> listar();
    List<ListItem> listarPorCliente(Integer idCliente);
    Response obtenerPorId(Integer id);
    Response crear(Request request);
    Response actualizar(Integer id, Request request);
    void eliminar(Integer id);
}
