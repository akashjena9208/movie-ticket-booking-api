package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.ScreenRequest;
import com.akash.moviebooking.api.dto.ScreenResponse;
import com.akash.moviebooking.api.service.ScreenService;
import com.akash.moviebooking.api.util.ResponseStructure;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ScreenController {

    private final ScreenService screenService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping("theaters/{theaterId}/screens")
   // @PreAuthorize("hasAuthority('THEATER_OWNER')")
    public ResponseEntity<ResponseStructure<ScreenResponse>> addScreen(@RequestBody @Valid ScreenRequest screenRequest, @PathVariable String theaterId){
        ScreenResponse screenResponse = screenService.addScreen(screenRequest, theaterId);
        return responseBuilder.sucess(HttpStatus.OK, "Screen has been successfully created", screenResponse);
    }

    @GetMapping("theaters/{theaterId}/screens/{screenId}")
    public ResponseEntity<ResponseStructure<ScreenResponse>> findScreen(@PathVariable String theaterId, @PathVariable String screenId){
        ScreenResponse screenResponse = screenService.findScreen(theaterId, screenId);
        return responseBuilder.sucess(HttpStatus.OK, "Screen has been successfully fetched", screenResponse);
    }




}