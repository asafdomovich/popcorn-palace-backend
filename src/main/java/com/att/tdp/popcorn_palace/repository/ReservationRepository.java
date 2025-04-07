package com.att.tdp.popcorn_palace.repository;

import com.att.tdp.popcorn_palace.entity.Reservation;
import com.att.tdp.popcorn_palace.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
	
	boolean existsByShowtimeAndSeatNumber(Showtime showtime, int seatNumber);
}
