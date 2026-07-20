package com.harish.subscriptiontracker.dto;

import com.harish.subscriptiontracker.entity.Subscription;
import com.harish.subscriptiontracker.entity.enums.Category;
import com.harish.subscriptiontracker.entity.enums.Currency;
import com.harish.subscriptiontracker.entity.enums.Frequency;
import com.harish.subscriptiontracker.entity.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SubscriptionResponse(
        Long id,
        String name,
        Double price,
        Currency currency,
        Frequency frequency,
        Category category,
        String paymentMethod,
        Status status,
        LocalDate startDate,
        LocalDate endDate,
        LocalDate renewalDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static SubscriptionResponse from(Subscription subscription) {
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getName(),
                subscription.getPrice(),
                subscription.getCurrency(),
                subscription.getFrequency(),
                subscription.getCategory(),
                subscription.getPaymentMethod(),
                subscription.getStatus(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getRenewalDate(),
                subscription.getCreatedAt(),
                subscription.getUpdatedAt()
        );
    }
}
