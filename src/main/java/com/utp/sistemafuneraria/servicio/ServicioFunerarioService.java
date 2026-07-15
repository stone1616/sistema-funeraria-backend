package com.utp.sistemafuneraria.servicio;

import java.util.List;
import java.util.Optional;

import com.utp.sistemafuneraria.servicio.ServicioFunerarioDTO.ListItem;
import com.utp.sistemafuneraria.servicio.ServicioFunerarioDTO.Request;
import com.utp.sistemafuneraria.servicio.ServicioFunerarioDTO.Response;

public interface ServicioFunerarioService {

    List<ListItem> listar();

    Response obtenerPorId(Integer id);

    Optional<Response> obtenerPorDifunto(Integer idDifunto);

    Response crear(Request request);

    Response actualizar(Integer id, Request request);

    void eliminar(Integer id);
}
