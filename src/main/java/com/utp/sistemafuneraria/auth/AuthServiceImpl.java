package com.utp.sistemafuneraria.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.utp.sistemafuneraria.auth.dto.LoginRequest;
import com.utp.sistemafuneraria.auth.dto.LoginResponse;
import com.utp.sistemafuneraria.personal.Personal;
import com.utp.sistemafuneraria.personal.PersonalRepository;
import com.utp.sistemafuneraria.shared.exception.ApiException;
import com.utp.sistemafuneraria.shared.security.JwtUtil;
import com.utp.sistemafuneraria.usuario.Usuario;
import com.utp.sistemafuneraria.usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PersonalRepository personalRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmailAndFechaEliminacionIsNull(request.email())
                .orElseThrow(() -> new ApiException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(request.password(), usuario.getPassword())) {
            throw new ApiException("Credenciales incorrectas");
        }

        Personal personal = personalRepository.findById(usuario.getIdPersonal())
                .orElseThrow(() -> new ApiException("El usuario no tiene un personal asociado válido"));

        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol());

        return new LoginResponse(
                token,
                usuario.getEmail(),
                usuario.getRol(),
                personal.getNombres(),
                personal.getApellidos()
        );
    }
}
