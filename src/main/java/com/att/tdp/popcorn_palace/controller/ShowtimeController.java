package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.request.CreateShowtimeRequest;
import com.att.tdp.popcorn_palace.dto.response.ShowtimeResponse;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    // Get showtime by ID
    @GetMapping("/{showtimeId}")
    public ResponseEntity<ShowtimeResponse> getShowtimeById(@PathVariable Long showtimeId) {
        ShowtimeResponse showtimeResponse = showtimeService.getShowtimeById(showtimeId);
        return new ResponseEntity<>(showtimeResponse, HttpStatus.OK);
    }

    // Add a new showtime
    @PostMapping
    public ResponseEntity<ShowtimeResponse> addShowtime(@Valid @RequestBody CreateShowtimeRequest createShowtimeRequest) {
        ShowtimeResponse showtimeResponse = showtimeService.addShowtime(createShowtimeRequest);
        return new ResponseEntity<>(showtimeResponse, HttpStatus.OK);
    }

    // Update a showtime
    @PostMapping("/update/{showtimeId}")
    public ResponseEntity<ShowtimeResponse> updateShowtime(@PathVariable Long showtimeId, @Valid @RequestBody CreateShowtimeRequest updateShowtimeRequest) {
        return new ResponseEntity<>(showtimeService.updateShowtime(showtimeId, updateShowtimeRequest), HttpStatus.OK);
    }

    // Delete a showtime
    @DeleteMapping("/{showtimeId}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long showtimeId) {
        showtimeService.deleteShowtime(showtimeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
