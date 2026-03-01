
package com.akash.moviebooking.api.entity;
import com.akash.moviebooking.api.enums.PaymentMethod;
import com.akash.moviebooking.api.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;


//@Entity
//@Getter
//@Setter
//@ToString
//@EntityListeners(AuditingEntityListener.class)
//public class Payment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "payment_id")
//    private String paymentId;
//
//    @ManyToOne
//    @JoinColumn(name = "booking_id", nullable = false)
//    private Booking booking;
//
//    @Column(name = "amount", nullable = false)
//    private Double amount;
//
//    @Column(name = "currency", nullable = false)
//    private String currency;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "payment_method", nullable = false)
//    private PaymentMethod paymentMethod; // e.g. UPI, CARD, NETBANKING
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", nullable = false)
//    private PaymentStatus status;  // ✅ keep only this one
//
//    @Column(name = "transaction_id")
//    private String transactionId; // from gateway
//
//    @CreatedDate
//    @Column(name = "created_at", updatable = false, nullable = false)
//    private Instant createdAt;
//
//    @LastModifiedDate
//    @Column(name = "updated_at", nullable = false)
//    private Instant updatedAt;
//}

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String transactionId;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}