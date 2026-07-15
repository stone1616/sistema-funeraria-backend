package com.utp.sistemafuneraria.usuario;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utp.sistemafuneraria.shared.response.ApiResponse;
import com.utp.sistemafuneraria.usuario.UsuarioDTO.CreateRequest;
import com.utp.sistemafuneraria.usuario.UsuarioDTO.EstadoRequest;
import com.utp.sistemafuneraria.usuario.UsuarioDTO.ListItem;
import com.utp.sistemafuneraria.usuario.UsuarioDTO.Response;
import com.utp.sistemafuneraria.usuario.UsuarioDTO.UpdateRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ListItem>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok(usuarioService.listar()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Response>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(usuarioService.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Response>> crear(@Valid @RequestBody CreateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(usuarioService.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Response>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(usuarioService.actualizar(id, request)));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<Response>> cambiarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody EstadoRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(usuarioService.cambiarEstado(id, request.estado())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        usuarioService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
