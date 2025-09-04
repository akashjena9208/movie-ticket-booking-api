package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.TheaterRequest;
import com.akash.moviebooking.api.dto.TheaterResponse;
import com.akash.moviebooking.api.service.TheaterService;
import com.akash.moviebooking.api.util.ResponseStructure;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
public class TheaterController {

    private final TheaterService theaterService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping("/theaters")
    //@PreAuthorize("hasAuthority('THEATER_OWNER')")
    public ResponseEntity<ResponseStructure<TheaterResponse>> addTheater(@RequestParam String email, @Valid @RequestBody TheaterRequest theaterRequest){
        TheaterResponse theaterResponse = theaterService.addTheater(email, theaterRequest);
        return responseBuilder.sucess(HttpStatus.OK, "Theater has been succesfull created", theaterResponse);
    }

    @GetMapping("theaters/{theaterId}")
    public ResponseEntity<ResponseStructure<TheaterResponse>> findTheater(@PathVariable String theaterId){
        TheaterResponse theaterResponse = theaterService.findTheater(theaterId);
        return responseBuilder.sucess(HttpStatus.OK, "Theater has been sucessfully fetched", theaterResponse);
    }

    @PutMapping("/theaters/{theaterId}")
    //@PreAuthorize("hasAuthority('THEATER_OWNER')")
    public ResponseEntity<ResponseStructure<TheaterResponse>> updateTheater(@PathVariable String theaterId, @Valid @RequestBody TheaterRequest registerationRequest){
        TheaterResponse theaterResponse = theaterService.updateTheater(theaterId, registerationRequest);
        return responseBuilder.sucess(HttpStatus.OK, "Theater has been sucessfully Updated", theaterResponse);
    }

}
