package com.herotech.app.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private Object data;

    public static ApiResponse ok(Object body) {
        return new ApiResponse(body);
    }
}
