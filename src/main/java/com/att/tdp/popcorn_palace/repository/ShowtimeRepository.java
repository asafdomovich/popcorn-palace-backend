package com.att.tdp.popcorn_palace.repository;

import com.att.tdp.popcorn_palace.entity.Showtime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    // You can add custom queries for retrieving showtimes by specific attributes, for example:
    // List<Showtime> findByTheaterAndStartTimeAfter(String theater, ZonedDateTime startTime);
}
