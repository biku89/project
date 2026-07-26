package com.example.demo.controller;

import com.example.demo.model.Reservation;
import com.example.demo.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create")
    public Reservation createReservation(@RequestBody Reservation reservation) {
         return reservationService.createReservation(reservation);
    }

    @GetMapping("/get")
    public List<Reservation> getAllReservation() {
        return reservationService.getAllReservation();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        return reservationService.getReservation(id)
                .map(reservation -> ResponseEntity.ok(reservation))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id,
                                                         @RequestBody Reservation reservation) {
        return reservationService.updateReservation(id, reservation)
                .map(r -> ResponseEntity.ok(r))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
