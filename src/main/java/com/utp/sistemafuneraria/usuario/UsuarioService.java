package com.utp.sistemafuneraria.usuario;

import java.util.List;

import com.utp.sistemafuneraria.usuario.UsuarioDTO.CreateRequest;
import com.utp.sistemafuneraria.usuario.UsuarioDTO.ListItem;
import com.utp.sistemafuneraria.usuario.UsuarioDTO.Response;
import com.utp.sistemafuneraria.usuario.UsuarioDTO.UpdateRequest;

public interface UsuarioService {
    List<ListItem> listar();
    Response obtenerPorId(Integer id);
    Response crear(CreateRequest request);
    Response actualizar(Integer id, UpdateRequest request);
    Response cambiarEstado(Integer id, Boolean estado);
    void eliminar(Integer id);
}
