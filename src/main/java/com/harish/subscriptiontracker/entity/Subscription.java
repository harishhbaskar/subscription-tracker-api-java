package com.harish.subscriptiontracker.entity;

import com.harish.subscriptiontracker.entity.enums.Category;
import com.harish.subscriptiontracker.entity.enums.Currency;
import com.harish.subscriptiontracker.entity.enums.Frequency;
import com.harish.subscriptiontracker.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.INR;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequency frequency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private LocalDate renewalDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void calculateRenewalAndStatus() {
        if (renewalDate == null && frequency != null) {
            int days = switch (frequency) {
                case DAILY -> 1;
                case WEEKLY -> 7;
                case MONTHLY -> 30;
                case YEARLY -> 365;
            };
            this.renewalDate = startDate.plusDays(days);
        }
        if (renewalDate != null && renewalDate.isBefore(LocalDate.now()) && status != Status.CANCELLED) {
            this.status = Status.EXPIRED;
        }
    }
}
