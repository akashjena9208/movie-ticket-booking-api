package com.akash.moviebooking.api.repository;

import com.akash.moviebooking.api.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    List<Payment> findByBooking_BookingId(String bookingId);
}