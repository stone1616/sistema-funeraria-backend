package com.utp.sistemafuneraria.recursos;

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

import com.utp.sistemafuneraria.recursos.SalaDTO.ListItem;
import com.utp.sistemafuneraria.recursos.SalaDTO.Request;
import com.utp.sistemafuneraria.recursos.SalaDTO.Response;
import com.utp.sistemafuneraria.shared.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sala")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService salaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ListItem>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok(salaService.listar()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Response>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(salaService.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Response>> crear(@Valid @RequestBody Request request) {
        return ResponseEntity.ok(ApiResponse.ok(salaService.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Response>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Request request) {
        return ResponseEntity.ok(ApiResponse.ok(salaService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        salaService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
