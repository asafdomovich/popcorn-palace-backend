package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.request.CreateMovieRequest;
import com.att.tdp.popcorn_palace.dto.response.MovieResponse;
import com.att.tdp.popcorn_palace.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    // Get all movies
    @GetMapping("/all")
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        List<MovieResponse> movieResponses = movieService.getAllMovies();
        return new ResponseEntity<>(movieResponses, HttpStatus.OK);
    }

    // Add a new movie
    @PostMapping
    public ResponseEntity<MovieResponse> addMovie(@Valid @RequestBody CreateMovieRequest createMovieRequest) {
        MovieResponse movieResponse = movieService.addMovie(createMovieRequest);
        return new ResponseEntity<>(movieResponse, HttpStatus.OK);
    }

    // Update a movie
    @PostMapping("/update/{movieTitle}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable String movieTitle, @Valid @RequestBody CreateMovieRequest updateMovieRequest) {
        return new ResponseEntity<>(movieService.updateMovie(movieTitle, updateMovieRequest), HttpStatus.OK);
    }

    // Delete a movie
    @DeleteMapping("/{movieTitle}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String movieTitle) {
        movieService.deleteMovie(movieTitle);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
