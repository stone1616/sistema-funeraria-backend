package com.utp.sistemafuneraria.servicio;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utp.sistemafuneraria.servicio.ServicioFunerarioDTO.ListItem;
import com.utp.sistemafuneraria.servicio.ServicioFunerarioDTO.Request;
import com.utp.sistemafuneraria.servicio.ServicioFunerarioDTO.Response;
import com.utp.sistemafuneraria.shared.exception.ResourceNotFoundException;
import com.utp.sistemafuneraria.shared.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servicio")
@RequiredArgsConstructor
public class ServicioFunerarioController {

    private final ServicioFunerarioService servicioService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ListItem>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok(servicioService.listar()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Response>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(servicioService.obtenerPorId(id)));
    }

    @GetMapping("/difunto/{idDifunto}")
    public ResponseEntity<ApiResponse<Response>> obtenerPorDifunto(@PathVariable Integer idDifunto) {
        Response response = servicioService.obtenerPorDifunto(idDifunto)
                .orElseThrow(() -> new ResourceNotFoundException("El difunto no tiene un servicio registrado"));
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Response>> crear(@Valid @RequestBody Request request) {
        return ResponseEntity.ok(ApiResponse.ok(servicioService.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Response>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Request request) {
        return ResponseEntity.ok(ApiResponse.ok(servicioService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        servicioService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
