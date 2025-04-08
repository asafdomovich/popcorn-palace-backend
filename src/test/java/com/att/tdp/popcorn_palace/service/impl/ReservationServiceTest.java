package com.att.tdp.popcorn_palace.service.impl;

import com.att.tdp.popcorn_palace.dto.request.CreateReservationRequest;
import com.att.tdp.popcorn_palace.dto.response.ReservationResponse;
import com.att.tdp.popcorn_palace.entity.Reservation;
import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.entity.User;
import com.att.tdp.popcorn_palace.exception.DataNotFoundException;
import com.att.tdp.popcorn_palace.repository.ReservationRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {
    
    @InjectMocks
    private ReservationServiceImpl reservationService;
    
    @Mock
    private ReservationRepository reservationRepository;
    
    @Mock
    private ShowtimeRepository showtimeRepository;
    
    @Mock
    private UserRepository userRepository;
    
    private UUID userId;
    private UUID bookingId;
    private Long showtimeId;
    
    private User user;
    private Showtime showtime;
    private CreateReservationRequest createReservationRequest;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        userId = UUID.randomUUID();      // UUID for userId
        bookingId = UUID.randomUUID();   // UUID for bookingId
        showtimeId = 1L;                 // Long for showtimeId
        
        user = new User(userId, "John Doe", "john.doe@example.com");
        showtime = new Showtime(showtimeId, "Theater 1", 12.99, null, null, null);
        
        createReservationRequest = new CreateReservationRequest(1, userId, showtimeId);
    }
    
    @Test
    void createReservation_ShouldReturnReservationResponse_WhenReservationIsCreatedSuccessfully() {
        // Mock the repositories
        when(showtimeRepository.findById(showtimeId)).thenReturn(Optional.of(showtime));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reservationRepository.existsByShowtimeAndSeatNumber(showtime, 1)).thenReturn(false);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation(bookingId, 1, user, showtime));
        
        // Call the method
        ReservationResponse response = reservationService.createReservation(createReservationRequest);
        
        // Verify the result
        assertNotNull(response);
        assertEquals(bookingId, response.getBookingId());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }
    
    @Test
    void createReservation_ShouldThrowException_WhenShowtimeNotFound() {
        // Mock the repositories
        when(showtimeRepository.findById(showtimeId)).thenReturn(Optional.empty());
        
        // Call the method and verify exception
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () ->
                reservationService.createReservation(createReservationRequest)
        );
        assertEquals("Showtime not found with id: " + showtimeId, exception.getMessage());
    }
    
    @Test
    void createReservation_ShouldThrowException_WhenUserNotFound() {
        // Mock the repositories
        when(showtimeRepository.findById(showtimeId)).thenReturn(Optional.of(showtime));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        
        // Call the method and verify exception
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () ->
                reservationService.createReservation(createReservationRequest)
        );
        assertEquals("User not found with id: " + userId, exception.getMessage());
    }
    
    @Test
    void createReservation_ShouldThrowException_WhenSeatAlreadyBooked() {
        // Mock the repositories
        when(showtimeRepository.findById(showtimeId)).thenReturn(Optional.of(showtime));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reservationRepository.existsByShowtimeAndSeatNumber(showtime, 1)).thenReturn(true);
        
        // Call the method and verify exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                reservationService.createReservation(createReservationRequest)
        );
        assertEquals("Seat already booked for the selected showtime", exception.getMessage());
    }
}
