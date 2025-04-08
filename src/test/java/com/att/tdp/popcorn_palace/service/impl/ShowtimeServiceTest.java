package com.att.tdp.popcorn_palace.service.impl;

import com.att.tdp.popcorn_palace.dto.request.CreateShowtimeRequest;
import com.att.tdp.popcorn_palace.dto.response.ShowtimeResponse;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.exception.DataNotFoundException;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShowtimeServiceTest {
    
    @InjectMocks
    private ShowtimeServiceImpl showtimeService;
    
    @Mock
    private ShowtimeRepository showtimeRepository;
    
    @Mock
    private MovieRepository movieRepository;
    
    private Movie movie;
    private Showtime showtime;
    private CreateShowtimeRequest createShowtimeRequest;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Sample Movie for testing
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");
        movie.setGenre("Action");
        movie.setDuration(120);
        movie.setRating(8.5);
        movie.setReleaseYear(2023);
        
        // Sample Showtime for testing
        showtime = new Showtime();
        showtime.setId(1L);
        showtime.setTheater("Theater 1");
        showtime.setPrice(15.99);
        showtime.setStartTime(LocalDateTime.now().plusHours(1));
        showtime.setEndTime(LocalDateTime.now().plusHours(2));
        showtime.setMovie(movie);
        
        // CreateShowtimeRequest for testing
        createShowtimeRequest = new CreateShowtimeRequest();
        createShowtimeRequest.setMovieId(1L);
        createShowtimeRequest.setTheater("Theater 1");
        createShowtimeRequest.setPrice(15.99);
        createShowtimeRequest.setStartTime(LocalDateTime.now().plusHours(1));
        createShowtimeRequest.setEndTime(LocalDateTime.now().plusHours(2));
    }
    
    @Test
    void addShowtime_ShouldReturnShowtimeResponse() {
        // Mock movieRepository to return a movie when its ID is requested
        when(movieRepository.findById(createShowtimeRequest.getMovieId())).thenReturn(Optional.of(movie));
        
        // Mock showtimeRepository to return a saved showtime
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(showtime);
        
        // Call the service method
        ShowtimeResponse response = showtimeService.addShowtime(createShowtimeRequest);
        
        // Validate the response
        assertNotNull(response);
        assertEquals(showtime.getId(), response.getId());
        assertEquals(showtime.getTheater(), response.getTheater());
        assertEquals(showtime.getPrice(), response.getPrice());
        assertEquals(showtime.getStartTime(), response.getStartTime());
        assertEquals(showtime.getEndTime(), response.getEndTime());
        assertEquals(showtime.getMovie().getId(), response.getMovieId());
        
        // Verify the interactions with repositories
        verify(movieRepository, times(1)).findById(createShowtimeRequest.getMovieId());
        verify(showtimeRepository, times(1)).save(any(Showtime.class));
    }
    
    @Test
    void addShowtime_MovieNotFound_ShouldThrowException() {
        // Mock movieRepository to return an empty Optional (movie not found)
        when(movieRepository.findById(createShowtimeRequest.getMovieId())).thenReturn(Optional.empty());
        
        // Call the service method and verify exception is thrown
        assertThrows(DataNotFoundException.class, () -> showtimeService.addShowtime(createShowtimeRequest));
        
        // Verify the repository interactions
        verify(movieRepository, times(1)).findById(createShowtimeRequest.getMovieId());
        verify(showtimeRepository, times(0)).save(any(Showtime.class));
    }
}
