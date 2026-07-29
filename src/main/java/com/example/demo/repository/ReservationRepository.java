package com.example.demo.repository;

import com.example.demo.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r where r.startTime < :newEnd AND r.endTime > :newStart")
    List<Reservation> findOverlapping(@Param("newStart") LocalDateTime newStart,
                                      @Param("newEnd") LocalDateTime newEnd);

    @Query("SELECT r FROM Reservation r where r.startTime < :newEnd AND r.endTime > :newStart AND r.id != :excludeId")
    List<Reservation> findOverlappingExcludingId(@Param("newStart") LocalDateTime newStart,
                                                 @Param("newEnd") LocalDateTime newEnd,
                                                 @Param("excludeId") Long excludeId);

}
