package com.utp.sistemafuneraria.recursos.dao;

import java.util.List;
import java.util.Optional;
import com.utp.sistemafuneraria.recursos.Sala;

public interface SalaDAO {

    List<Sala> listar();
    Optional<Sala> buscarPorId(Integer id);
    Sala insertar(Sala sala);
    Sala actualizar(Sala sala);
    void eliminar(Integer id);
    

}
