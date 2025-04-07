package com.att.tdp.popcorn_palace.repository;

import com.att.tdp.popcorn_palace.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // You can add custom queries if needed.
}
