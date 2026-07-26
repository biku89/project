package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    private String customerName;
    private String serviceType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Reservation(Long id, String customerName, String serviceType, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.customerName = customerName;
        this.serviceType = serviceType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Reservation() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(customerName, that.customerName) && Objects.equals(serviceType, that.serviceType) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerName, serviceType, startTime, endTime);
    }
}
