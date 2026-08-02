package com.example.demo.dto;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        Long customerId,
        String customerName,
        String serviceType,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
