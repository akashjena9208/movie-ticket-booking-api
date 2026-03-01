//package com.akash.moviebooking.api.mapper;
//
//import com.akash.moviebooking.api.dto.BookingResponseDto;
//import com.akash.moviebooking.api.entity.Booking;
//import com.akash.moviebooking.api.entity.Seat;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BookingMapper {
//
//    public BookingResponseDto toDto(Booking booking) {
//        return new BookingResponseDto(
//                booking.getBookingId(),
//                booking.getBookingStatus().name(),
//                booking.getTotalAmount(),
//                booking.getUser().getUserId(),
//                booking.getShow().getShowId(),
//                booking.getSeats().stream().map(Seat::getSeatId).toList(),
//                booking.getCreatedAt(),
//                booking.getUpdatedAt()
//        );
//    }
//}
package com.akash.moviebooking.api.mapper;

import com.akash.moviebooking.api.dto.BookingResponseDto;
import com.akash.moviebooking.api.entity.Booking;
import com.akash.moviebooking.api.entity.Seat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingMapper {

    public BookingResponseDto toDto(Booking booking) {

        if (booking == null) return null;

        List<String> seatIds = booking.getSeats() != null
                ? booking.getSeats().stream()
                .map(Seat::getSeatId)
                .toList()
                : null;

        return new BookingResponseDto(
                booking.getBookingId(),
                booking.getBookingStatus(),
                booking.getTotalAmount(),
                booking.getUser() != null ? booking.getUser().getUserId() : null,
                booking.getShow() != null ? booking.getShow().getShowId() : null,
                seatIds,
                booking.getCreatedAt(),
                booking.getUpdatedAt()
        );
    }
}