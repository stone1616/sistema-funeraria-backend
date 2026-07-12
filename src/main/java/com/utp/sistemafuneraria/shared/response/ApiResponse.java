package com.utp.sistemafuneraria.shared.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private T data;
    private String message;

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data, "OK");
    }

    public static <T> ApiResponse<T> of(T data, String message) {
        return new ApiResponse<>(data, message);
    }

    public static ApiResponse<Void> error(String message) {
        return new ApiResponse<>(null, message);
    }

}
