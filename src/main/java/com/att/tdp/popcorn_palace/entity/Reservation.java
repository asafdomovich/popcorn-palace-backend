package com.att.tdp.popcorn_palace.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"showtime_id", "seatNumber"})
})
public class Reservation {
    
    @Id
    @GeneratedValue
    private UUID bookingId;

    private int seatNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id")
    private Showtime showtime;
}
