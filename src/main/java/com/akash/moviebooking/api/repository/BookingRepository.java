////package com.akash.moviebooking.api.repository;
////
////import com.akash.moviebooking.api.entity.Booking;
////import org.springframework.data.jpa.repository.JpaRepository;
////
////import org.springframework.stereotype.Repository;
////
////import java.util.List;
////
////@Repository
////public interface BookingRepository extends JpaRepository<Booking, String> {
////    List<Booking> findByUser_UserId(String userId);
////}
//package com.akash.moviebooking.api.repository;
//
//import com.akash.moviebooking.api.entity.Booking;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface BookingRepository extends JpaRepository<Booking, String> {
//
//    List<Booking> findByUser_UserId(String userId);
//    @Query("""
//    SELECT b FROM Booking b
//    JOIN FETCH b.user
//    JOIN FETCH b.show
//    LEFT JOIN FETCH b.seats
//    WHERE b.user.userId = :userId
//""")
//    List<Booking> findBookingsWithDetails(@Param("userId") String userId);
//
//}
package com.akash.moviebooking.api.repository;

import com.akash.moviebooking.api.entity.Booking;
import com.akash.moviebooking.api.entity.Seat;
import com.akash.moviebooking.api.entity.Show;
import com.akash.moviebooking.api.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {

    List<Booking> findByUser_UserId(String userId);

    boolean existsByShowAndSeatsInAndBookingStatus(
            Show show,
            List<Seat> seats,
            BookingStatus status
    );
}