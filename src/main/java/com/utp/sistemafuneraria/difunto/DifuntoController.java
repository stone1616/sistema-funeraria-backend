package com.utp.sistemafuneraria.difunto;

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

import com.utp.sistemafuneraria.difunto.DifuntoDTO.ListItem;
import com.utp.sistemafuneraria.difunto.DifuntoDTO.Request;
import com.utp.sistemafuneraria.difunto.DifuntoDTO.Response;
import com.utp.sistemafuneraria.shared.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/difunto")
@RequiredArgsConstructor
public class DifuntoController {

    private final DifuntoService difuntoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ListItem>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok(difuntoService.listar()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Response>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(difuntoService.obtenerPorId(id)));
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<ApiResponse<List<ListItem>>> listarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(ApiResponse.ok(difuntoService.listarPorCliente(idCliente)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Response>> crear(@Valid @RequestBody Request request) {
        return ResponseEntity.ok(ApiResponse.ok(difuntoService.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Response>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Request request) {
        return ResponseEntity.ok(ApiResponse.ok(difuntoService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        difuntoService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
