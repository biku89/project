package com.example.demo.service;

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

    public Reservation createReservation(Reservation reservation) {
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

    public Optional<Reservation> updateReservation(Long id, Reservation reservation) {
        Optional<Reservation> current = reservationRepository.findById(id);

        return current.map(existing -> {
            existing.setCustomerName(reservation.getCustomerName());
            existing.setServiceType(reservation.getServiceType());
            existing.setStartTime(reservation.getStartTime());
            existing.setEndTime(reservation.getEndTime());
            return reservationRepository.save(existing);
        });
    }
}
