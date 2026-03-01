package com.akash.moviebooking.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
//
//@Entity
//@EntityListeners(AuditingEntityListener.class)
//@Setter
//@Getter
////@ToString
//public class Theater {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "theater_id")
//    private String theaterId;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "address")
//    private String address;
//
//    @Column(name = "city")
//    private String city;
//
//    @Column(name = "landmark")
//    private String landmark;
//
//    @ManyToOne
//    @JoinColumn(name = "theater_owner_id")
//    private TheaterOwner theaterOwner;
//
//    @OneToMany(mappedBy = "theater")
//    private List<Screen> screens;
//
//    @OneToMany(mappedBy = "theater")
//    private List<Show> shows;
//
//
//    @CreatedDate
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private Instant createdAt;
//
//    @LastModifiedDate
//    @Column(name = "updated_at", nullable = false)
//    private Instant updatedAt;
//
//    @CreatedBy
//    private String createdBy;
//
//
//}
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String theaterId;

    private String name;
    private String address;
    private String city;
    private String landmark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_owner_id")
    private TheaterOwner theaterOwner;

    @OneToMany(mappedBy = "theater", fetch = FetchType.LAZY)
    private List<Screen> screens;

    @OneToMany(mappedBy = "theater", fetch = FetchType.LAZY)
    private List<Show> shows;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @CreatedBy
    private String createdBy;
}