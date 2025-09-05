package com.akash.moviebooking.api.service.impl;

import com.akash.moviebooking.api.dto.ScreenRequest;
import com.akash.moviebooking.api.dto.ScreenResponse;
import com.akash.moviebooking.api.entity.Screen;
import com.akash.moviebooking.api.entity.Seat;
import com.akash.moviebooking.api.repository.SeatRepository;
import com.akash.moviebooking.api.entity.Theater;
import com.akash.moviebooking.api.exceptions.NoOfRowsExceedCapacityException;
import com.akash.moviebooking.api.exceptions.ScreenNotFoundByIdException;
import com.akash.moviebooking.api.exceptions.TheaterNotFoundByIdException;
import com.akash.moviebooking.api.mapper.ScreenMapper;
import com.akash.moviebooking.api.repository.ScreenRepository;
import com.akash.moviebooking.api.repository.TheaterRepository;
import com.akash.moviebooking.api.service.ScreenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScreenServiceImpl implements ScreenService {
    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final ScreenMapper screenMapper;


    @Override
    public ScreenResponse addScreen(ScreenRequest screenRequest, String theaterId) {

        if (theaterRepository.existsById(theaterId)) {
            Theater theater = theaterRepository.findById(theaterId).get();
            Screen screen = copy(screenRequest, new Screen(), theater);
            return screenMapper.screenResponseMapper(screen);
        }

        throw new TheaterNotFoundByIdException("No Theater found by ID");

    }

    @Override
    public ScreenResponse findScreen(String theaterId, String screenId) {
        if (theaterRepository.existsById(theaterId)) {
            if (screenRepository.existsById(screenId)) {
                return screenMapper.screenResponseMapper(screenRepository.findById(screenId).get());
            }
            throw new ScreenNotFoundByIdException("Screen Not Found by Id");
        }
        throw new TheaterNotFoundByIdException("Theater not found by Id");
    }



    private Screen copy(ScreenRequest screenRequest, Screen screen, Theater theater) {
        screen.setScreenType(screenRequest.screenType());
        screen.setCapacity(screenRequest.capacity());
        if (screenRequest.noOfRows() > screenRequest.capacity()) //Validate rows ≤ capacity.
            throw new NoOfRowsExceedCapacityException("The no.of rows exceed the capacity");
        screen.setNoOfRows(screenRequest.noOfRows());
        screen.setTheater(theater);
        screenRepository.save(screen);//Save screen (empty seats).
        //Attach them after saving” = you first save the screen without seats, then later add the seats to the Java object. But unless you tell JPA to save again (or rely on cascade before first save), those seats don’t go into the database.
        screen.setSeats(createSeats(screen, screenRequest.capacity())); //Create seats and attach them after saving.
        return screen;
    }
    private List<Seat> createSeats(Screen screen, Integer capacity) {
        List<Seat> seats = new LinkedList<>();
        int noOfSeatsPerRow = screen.getCapacity() / screen.getNoOfRows();
        char row = 'A';
        for (int i = 1, j = 1; i <= capacity; i++, j++) {
            Seat seat = new Seat();

            seat.setScreen(screen);        // link this seat to the screen
            seat.setDelete(false);         // mark seat as active (not deleted)
            seat.setName(row + "" + j);    // assign seat name like A1, A2, B1...

            //seatRepository.save(seat);     // save this seat immediately in DB
             seats.add(seat);        // collect seats       // keep track in local List<Seat>

            if (j == noOfSeatsPerRow) {    // if row is full
                j = 0;                     // reset column counter
                row++;                     // go to next row (A -> B -> C...)
            }

            //Modify add this two line
            screen.setSeats(seats); //rAttach seats to screen
            screenRepository.save(screen); // This saves screen & seats at once due to cascade
            //Do not call seatRepository.save() for each seat.
            //Do not call screenRepository.save() inside the loop.
            // Only save once, after seats are attached
        }
        return seats;                      // return list of all created seats
    }

                                    //    Input: capacity = 12, noOfRows = 3
                                    //    Row A → A1 A2 A3 A4
                                    //    Row B → B1 B2 B3 B4
                                    //    Row C → C1 C2 C3 C4
                                    //    Output in DB:
                                    //            [A1, A2, A3, A4, B1, B2, B3, B4, C1, C2, C3, C4]


    /*🔎 What your code does
                            addScreen
                            Checks if theaterId exists.
                            Loads Theater from DB.
                            Calls copy() → builds a Screen + attaches seats.
                            Maps the result to ScreenResponse.

                            copy()
                            Sets screen type, capacity, rows.
                            Validates noOfRows <= capacity.
                            Associates the screen with the theater.
                            Saves the screen (so it now has an ID in DB).
                            Calls createSeats() to make seats.
                            Attaches created seats to the screen object.
                            Returns the screen.
                            createSeats()
                            Calculates how many seats per row:
                            int noOfSeatsPerRow = screen.getCapacity() / screen.getNoOfRows();
                            Example: capacity = 12, rows = 3 → 4 seats per row.
                            Creates seats in a loop:
                            Names them row by row (A1, A2, A3, A4 ...).
                            Saves each seat immediately in the DB using seatRepository.save(seat).
                            Keeps them in a local list.
                            When a row fills, moves to the next row (A → B → C).
                            Returns the full seat list.
                            ✅ What is correct
                            Validation (rows > capacity throws exception).
                            Seat naming convention (A1, A2 … B1, B2 …).
                            Linking Seat ↔ Screen.
                            Logic produces correct seat arrangement when capacity divides evenly by rows.
                            Mapping response with ScreenMapper.
                            ⚠️ What problems remain
                            N+1 Saves Problem (Performance)
                            seatRepository.save(seat) inside a loop = 1 DB call per seat.
                            300 seats → 300 inserts → slow.
                            Best practice: attach seats to screen and do one save on screen (with cascade = ALL).
                            Seats are not re-saved with screen
                            You screenRepository.save(screen) before creating seats.
                            Then you attach seats after.
                            Unless you explicitly save again (screenRepository.save(screen)), the JPA cascade will not persist those seats automatically.
                            That’s why you had to call seatRepository.save(seat) for each one.
                            Uneven Capacity Distribution
                            If capacity % noOfRows != 0, the last row may have fewer seats than others.
                            Example: capacity = 10, rows = 3 →
                            A1 A2 A3
                            B1 B2 A4  (naming may become strange depending on loop counters)
                            C1 C2 C3

                           Now i am solve

     */

}
