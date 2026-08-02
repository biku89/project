package com.example.demo.service;

import com.example.demo.dto.ReservationRequest;
import com.example.demo.dto.ReservationResponse;
import com.example.demo.exception.InvalidReservationException;
import com.example.demo.exception.ReservationConflictException;
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

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation();
        validateTimes(reservationRequest);
        validateNoOverlap(reservationRequest);
        applyRequest(reservation, reservationRequest);
        Reservation saved = reservationRepository.save(reservation);
        return toResponse(saved);
    }

    public List<ReservationResponse> getAllReservation() {
        return reservationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<ReservationResponse> getReservation(Long id) {
        return reservationRepository.findById(id).map(this::toResponse);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Optional<ReservationResponse> updateReservation(Long id, ReservationRequest reservationRequest) {
        validateTimes(reservationRequest);
        validateNoOverlapForUpdate(reservationRequest, id);
        Optional<Reservation> current = reservationRepository.findById(id);

        return current.map(existing -> {
            applyRequest(existing, reservationRequest);
            return toResponse(reservationRepository.save(existing));
        });
    }

    private void applyRequest(Reservation reservation, ReservationRequest reservationRequest) {
        reservation.setCustomerName(reservationRequest.customerName());
        reservation.setServiceType(reservationRequest.serviceType());
        reservation.setStartTime(reservationRequest.startTime());
        reservation.setEndTime(reservationRequest.endTime());
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getCustomerName(),
                reservation.getServiceType(),
                reservation.getStartTime(),
                reservation.getEndTime()
        );
    }

    private void validateTimes(ReservationRequest request) {
        if (!request.startTime().isBefore(request.endTime())) {
            throw new InvalidReservationException("endTime must be after startTime");
        }
    }

    private void validateNoOverlap(ReservationRequest request) {
        List<Reservation> overLapping = reservationRepository.findOverlapping(request.startTime(), request.endTime());
        if (!overLapping.isEmpty()) {
            throw new ReservationConflictException("The selected time overlaps with an existing reservation");
        }
    }

    private void validateNoOverlapForUpdate(ReservationRequest request, Long id) {
        List<Reservation> overLapping = reservationRepository.findOverlappingExcludingId(
                request.startTime(),
                request.endTime(),
                id);
        if (!overLapping.isEmpty()) {
            throw new ReservationConflictException("The selected time overlaps with an existing reservation");
        }
    }
}
