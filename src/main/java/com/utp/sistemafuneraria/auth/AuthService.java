package com.utp.sistemafuneraria.auth;

import com.utp.sistemafuneraria.auth.dto.LoginRequest;
import com.utp.sistemafuneraria.auth.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
