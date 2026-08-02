package com.example.demo.dto;

public record CustomerResponse(
        Long id,
        String name,
        String phoneNumber,
        String email
) {

}
