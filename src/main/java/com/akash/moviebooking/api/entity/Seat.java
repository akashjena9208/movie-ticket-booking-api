package com.akash.moviebooking.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "seat_id")
    private String seatId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "screen_id")
    private Screen screen;

//    @ManyToMany(mappedBy = "seats")
//    private List<Booking> bookings;

    @Column(name = "is_delete")
    private boolean isDelete;

    @Column(name = "deleted_at")
    private Instant deletedAt;


    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;


}

