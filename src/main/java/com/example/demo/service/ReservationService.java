package com.example.demo.service;

import com.example.demo.dto.ReservationRequest;
import com.example.demo.model.Reservation;
import com.example.demo.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation();
        applyRequest(reservation, reservationRequest);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservation(Long id) {
        return reservationRepository.findById(id);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Optional<Reservation> updateReservation(Long id, ReservationRequest reservationRequest) {

        Optional<Reservation> current = reservationRepository.findById(id);

        return current.map(existing -> {
            applyRequest(existing, reservationRequest);
            return reservationRepository.save(existing);
        });
    }

    private void applyRequest(Reservation reservation, ReservationRequest reservationRequest) {
        reservation.setCustomerName(reservationRequest.customerName());
        reservation.setServiceType(reservationRequest.serviceType());
        reservation.setStartTime(reservationRequest.startTime());
        reservation.setEndTime(reservationRequest.endTime());
    }
}
