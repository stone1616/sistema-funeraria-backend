package com.utp.sistemafuneraria.cliente;

import java.util.List;

import com.utp.sistemafuneraria.cliente.ClienteDTO.ListItem;
import com.utp.sistemafuneraria.cliente.ClienteDTO.Request;
import com.utp.sistemafuneraria.cliente.ClienteDTO.Response;

public interface ClienteService {
    // Retorna una lista de ListItem, un DTO con solo los datos necesarios para mostrar en una tabla
    List<ListItem> listar();

    // Busca un cliente por su id y retorna un Response con sus datos
    Response obtenerPorId(Integer id);

    // Busca un cliente por su DNI y retorna un Response con sus datos
    Response buscarPorDni(String dni);

    // Registra un nuevo cliente con los datos del Request y retorna un Response con la confirmación
    Response crear(Request request);

    // Actualiza los datos de un cliente existente por su ID y retorna un Response con la confirmación
    Response actualizar(Integer id, Request request);

    // Elimina lógicamente un cliente por su ID (asigna fechaEliminacion) y no retorna nada
    void eliminar(Integer id);
    
}
