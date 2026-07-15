package com.utp.sistemafuneraria.reservasala;

import java.util.List;

import com.utp.sistemafuneraria.reservasala.ReservaSalaDTO.ListItem;
import com.utp.sistemafuneraria.reservasala.ReservaSalaDTO.Request;
import com.utp.sistemafuneraria.reservasala.ReservaSalaDTO.Response;

public interface ReservaSalaService {

    List<ListItem> listar();

    Response obtenerPorId(Integer id);

    Response crear(Request request);

    Response actualizar(Integer id, Request request);

    void eliminar(Integer id);
}
