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
import com.att.tdp.popcorn_palace.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired private ReservationRepository reservationRepository;
    @Autowired private ShowtimeRepository showtimeRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public ReservationResponse createReservation(CreateReservationRequest createReservationRequest) {
        Showtime showtime = showtimeRepository.findById(createReservationRequest.getShowtimeId()).orElseThrow(
                () -> new DataNotFoundException("Showtime not found with id: " + createReservationRequest.getShowtimeId())
        );
        User user = userRepository.findById(createReservationRequest.getUserId()).orElseThrow(
                () -> new DataNotFoundException("User not found with id: " + createReservationRequest.getUserId())
        );

        // Check if seat number is already booked for the showtime
        if (reservationRepository.existsByShowtimeAndSeatNumber(showtime, createReservationRequest.getSeatNumber())) {
            throw new IllegalArgumentException("Seat already booked for the selected showtime");
        }

        Reservation reservation = new Reservation(null, createReservationRequest.getSeatNumber(), user, showtime);
        Reservation savedReservation = reservationRepository.save(reservation);
        return new ReservationResponse(savedReservation.getBookingId());
    }
}
