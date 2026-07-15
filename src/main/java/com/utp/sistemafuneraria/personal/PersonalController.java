package com.utp.sistemafuneraria.personal;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utp.sistemafuneraria.personal.PersonalDTO.CreateRequest;
import com.utp.sistemafuneraria.personal.PersonalDTO.ListItem;
import com.utp.sistemafuneraria.shared.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/personal")
@RequiredArgsConstructor
public class PersonalController {

    private final PersonalService personalService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ListItem>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok(personalService.listar()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ListItem>> crear(@Valid @RequestBody CreateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(personalService.crear(request)));
    }
}
