//package com.akash.moviebooking.api.service.impl;
//
//import com.akash.moviebooking.api.dto.ScreenRequest;
//import com.akash.moviebooking.api.dto.ScreenResponse;
//import com.akash.moviebooking.api.entity.Screen;
//import com.akash.moviebooking.api.entity.Seat;
//import com.akash.moviebooking.api.entity.Theater;
//import com.akash.moviebooking.api.exceptions.NoOfRowsExceedCapacityException;
//import com.akash.moviebooking.api.exceptions.ScreenNotFoundByIdException;
//import com.akash.moviebooking.api.exceptions.TheaterNotFoundByIdException;
//import com.akash.moviebooking.api.mapper.ScreenMapper;
//import com.akash.moviebooking.api.repository.ScreenRepository;
//import com.akash.moviebooking.api.repository.TheaterRepository;
//import com.akash.moviebooking.api.service.ScreenService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class ScreenServiceImpl implements ScreenService {
//
//    private final TheaterRepository theaterRepository;
//    private final ScreenRepository screenRepository;
//    private final ScreenMapper screenMapper;
//
//    @Override
//    public ScreenResponse addScreen(ScreenRequest screenRequest, String theaterId) {
//
//        Theater theater = theaterRepository.findById(theaterId)
//                .orElseThrow(() -> new TheaterNotFoundByIdException("Theater not found"));
//
//        if (screenRequest.noOfRows() > screenRequest.capacity()) {
//            throw new NoOfRowsExceedCapacityException("Number of rows cannot exceed capacity");
//        }
//
//        Screen screen = new Screen();
//        screen.setScreenType(screenRequest.screenType());
//        screen.setCapacity(screenRequest.capacity());
//        screen.setNoOfRows(screenRequest.noOfRows());
//        screen.setTheater(theater);
//
//        List<Seat> seats = createSeats(screen);
//        screen.setSeats(seats);
//
//        return screenMapper.screenResponseMapper(screenRepository.save(screen));
//    }
//
//    @Override
//    public ScreenResponse findScreen(String theaterId, String screenId) {
//
//        theaterRepository.findById(theaterId)
//                .orElseThrow(() -> new TheaterNotFoundByIdException("Theater not found"));
//
//        Screen screen = screenRepository.findById(screenId)
//                .orElseThrow(() -> new ScreenNotFoundByIdException("Screen not found"));
//
//        return screenMapper.screenResponseMapper(screen);
//    }
//
//    private List<Seat> createSeats(Screen screen) {
//
//        List<Seat> seats = new ArrayList<>();
//        int seatsPerRow = screen.getCapacity() / screen.getNoOfRows();
//        char row = 'A';
//
//        for (int i = 1, j = 1; i <= screen.getCapacity(); i++, j++) {
//
//            Seat seat = new Seat();
//            seat.setScreen(screen);
//            seat.setDelete(false);
//            seat.setName(row + "" + j);
//
//            seats.add(seat);
//
//            if (j == seatsPerRow) {
//                j = 0;
//                row++;
//            }
//        }
//
//        return seats;
//    }
//}
package com.akash.moviebooking.api.service.impl;

import com.akash.moviebooking.api.dto.ScreenRequest;
import com.akash.moviebooking.api.dto.ScreenResponse;
import com.akash.moviebooking.api.entity.Screen;
import com.akash.moviebooking.api.entity.Seat;
import com.akash.moviebooking.api.entity.Theater;
import com.akash.moviebooking.api.exceptions.NoOfRowsExceedCapacityException;
import com.akash.moviebooking.api.exceptions.ScreenNotFoundByIdException;
import com.akash.moviebooking.api.exceptions.TheaterNotFoundByIdException;
import com.akash.moviebooking.api.mapper.ScreenMapper;
import com.akash.moviebooking.api.repository.ScreenRepository;
import com.akash.moviebooking.api.repository.TheaterRepository;
import com.akash.moviebooking.api.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScreenServiceImpl implements ScreenService {

    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;
    private final ScreenMapper mapper;

    @Override
    public ScreenResponse addScreen(ScreenRequest request, String theaterId) {

        Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new TheaterNotFoundByIdException("Theater not found with the given ID."));

        if (request.noOfRows() > request.capacity()) {
            throw new NoOfRowsExceedCapacityException("Number of rows cannot exceed the total screen capacity.");
        }

        Screen screen = new Screen();
        screen.setScreenType(request.screenType());
        screen.setCapacity(request.capacity());
        screen.setNoOfRows(request.noOfRows());
        screen.setTheater(theater);

        screen.setSeats(generateSeats(screen));

        return mapper.toDto(screenRepository.save(screen));
    }

    @Override
    public ScreenResponse getScreen(String theaterId, String screenId) {

        theaterRepository.findById(theaterId).orElseThrow(() -> new TheaterNotFoundByIdException("Theater not found with the given ID."));

        Screen screen = screenRepository.findById(screenId).orElseThrow(() -> new ScreenNotFoundByIdException("Screen not found with the given ID."));

        return mapper.toDto(screen);
    }

    private List<Seat> generateSeats(Screen screen) {

        List<Seat> seats = new ArrayList<>();
        int seatsPerRow = screen.getCapacity() / screen.getNoOfRows();
        char row = 'A';

        for (int i = 1; i <= screen.getCapacity(); i++) {

            int seatNumber = ((i - 1) % seatsPerRow) + 1;

            if (seatNumber == 1 && i != 1) {
                row++;
            }

            Seat seat = new Seat();
            seat.setScreen(screen);
            seat.setDelete(false);
            seat.setName(row + String.valueOf(seatNumber));
            seat.setPrice(250.0);

            seats.add(seat);
        }

        return seats;
    }
}