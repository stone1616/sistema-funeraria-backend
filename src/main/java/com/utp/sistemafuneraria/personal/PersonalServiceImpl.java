package com.utp.sistemafuneraria.personal;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.personal.PersonalDTO.CreateRequest;
import com.utp.sistemafuneraria.personal.PersonalDTO.ListItem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonalServiceImpl implements PersonalService {

    private final PersonalRepository personalRepository;
    private static final Integer USUARIO_ACTUAL = 1;

    @Override
    public List<ListItem> listar() {
        return personalRepository.findByFechaEliminacionIsNull().stream()
                .map(this::toListItem)
                .toList();
    }

    @Override
    public ListItem crear(CreateRequest request) {
        Personal personal = new Personal();
        personal.setNombres(request.nombres());
        personal.setApellidos(request.apellidos());
        personal.setDni(request.dni());
        personal.setTelefono(request.telefono());
        personal.setDireccion(request.direccion());
        personal.setCargo(request.cargo());
        personal.setEstado("ACTIVO");
        personal.setFechaCreacion(LocalDateTime.now(ZoneId.of("America/Lima")));
        personal.setIdUsuarioCreacion(USUARIO_ACTUAL);

        return toListItem(personalRepository.save(personal));
    }

    private ListItem toListItem(Personal p) {
        return new ListItem(p.getIdPersonal(), p.getNombres(), p.getApellidos(), p.getDni(), p.getCargo());
    }
}
