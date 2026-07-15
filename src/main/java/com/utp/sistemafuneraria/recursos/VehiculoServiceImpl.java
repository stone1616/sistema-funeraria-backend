package com.utp.sistemafuneraria.recursos;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.recursos.VehiculoDTO.ListItem;
import com.utp.sistemafuneraria.recursos.VehiculoDTO.Request;
import com.utp.sistemafuneraria.recursos.VehiculoDTO.Response;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private static final Integer USUARIO_ACTUAL = 1;

    @Override
    public List<ListItem> listar() {
        return vehiculoRepository.findByFechaEliminacionIsNull().stream()
                .map(this::toListItem)
                .toList();
    }

    @Override
    public Response obtenerPorId(Integer id) {
        Vehiculo vehiculo = vehiculoRepository.findByIdVehiculoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo con id " + id + " no existe"));
        return toResponse(vehiculo);
    }

    @Override
    public Response crear(Request request) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(request.placa());
        vehiculo.setMarca(request.marca());
        vehiculo.setModelo(request.modelo());
        vehiculo.setCapacidad(request.capacidad());
        vehiculo.setEstado(request.estado());
        vehiculo.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        vehiculo.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toResponse(vehiculoRepository.save(vehiculo));
    }

    @Override
    public Response actualizar(Integer id, Request request) {
        Vehiculo vehiculo = vehiculoRepository.findByIdVehiculoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo con id " + id + " no existe"));

        vehiculo.setPlaca(request.placa());
        vehiculo.setMarca(request.marca());
        vehiculo.setModelo(request.modelo());
        vehiculo.setCapacidad(request.capacidad());
        vehiculo.setEstado(request.estado());
        vehiculo.setFechaModificacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        vehiculo.setIdUsuarioModificacion(USUARIO_ACTUAL);

        return toResponse(vehiculoRepository.save(vehiculo));
    }

    @Override
    public void eliminar(Integer id) {
        Vehiculo vehiculo = vehiculoRepository.findByIdVehiculoAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo con id " + id + " no existe"));

        vehiculo.setFechaEliminacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        vehiculo.setIdUsuarioEliminacion(USUARIO_ACTUAL);

        vehiculoRepository.save(vehiculo);
    }

    private Response toResponse(Vehiculo v) {
        return new Response(
                v.getIdVehiculo(), v.getPlaca(), v.getMarca(), v.getModelo(), v.getCapacidad(), v.getEstado(),
                v.getFechaCreacion(), v.getFechaModificacion(), v.getFechaEliminacion(),
                v.getIdUsuarioCreacion(), v.getIdUsuarioModificacion(), v.getIdUsuarioEliminacion()
        );
    }

    private ListItem toListItem(Vehiculo v) {
        return new ListItem(v.getIdVehiculo(), v.getPlaca(), v.getMarca(), v.getModelo(), v.getCapacidad(), v.getEstado());
    }
}
