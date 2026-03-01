//package com.akash.moviebooking.api.repository;
//
//import com.akash.moviebooking.api.entity.Payment;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//
//@Repository
//public interface PaymentRepository extends JpaRepository<Payment, String> {
//
//    List<Payment> findByBooking_BookingId(String bookingId);
//
////    @Query("SELECT p FROM Payment p WHERE p.booking.bookingId = :bookingId")
////    List<Payment> findByBookingId(@Param("bookingId") String bookingId);
//
//}
package com.akash.moviebooking.api.repository;

import com.akash.moviebooking.api.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    List<Payment> findByBooking_BookingId(String bookingId);
}