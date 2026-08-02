package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        @NotBlank
        String name,
        @NotBlank
        String phoneNumber,
        @NotBlank
        String  email
) {
}
