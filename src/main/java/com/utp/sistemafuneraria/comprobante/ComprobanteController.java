package com.utp.sistemafuneraria.comprobante;

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

import com.utp.sistemafuneraria.comprobante.ComprobanteDTO.ListItem;
import com.utp.sistemafuneraria.comprobante.ComprobanteDTO.Request;
import com.utp.sistemafuneraria.comprobante.ComprobanteDTO.Response;
import com.utp.sistemafuneraria.shared.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comprobante")
@RequiredArgsConstructor
public class ComprobanteController {

    private final ComprobanteService comprobanteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ListItem>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok(comprobanteService.listar()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Response>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(comprobanteService.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Response>> crear(@Valid @RequestBody Request request) {
        return ResponseEntity.ok(ApiResponse.ok(comprobanteService.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Response>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Request request) {
        return ResponseEntity.ok(ApiResponse.ok(comprobanteService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        comprobanteService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
