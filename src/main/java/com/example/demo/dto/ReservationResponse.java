package com.example.demo.dto;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        String customerName,
        String serviceType,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
