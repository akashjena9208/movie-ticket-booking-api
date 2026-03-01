package com.akash.moviebooking.api.entity;


import com.akash.moviebooking.api.enums.Certificate;
import com.akash.moviebooking.api.enums.Genre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String movieId;

    private String title;
    private String description;

    @ElementCollection
    private Set<String> castList;

    private Duration runtime;

    @Enumerated(EnumType.STRING)
    private Certificate certificate;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private List<Show> shows;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private UserDetails owner;
}