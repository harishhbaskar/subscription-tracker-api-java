package com.harish.subscriptiontracker.dto;

import com.harish.subscriptiontracker.entity.enums.Category;
import com.harish.subscriptiontracker.entity.enums.Currency;
import com.harish.subscriptiontracker.entity.enums.Frequency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateSubscriptionRequest(
        @NotBlank @Size(min = 2, max = 60)
        String name,

        @NotNull @PositiveOrZero
        Double price,

        Currency currency,

        @NotNull
        Frequency frequency,

        @NotNull
        Category category,

        @NotBlank
        String paymentMethod,

        @NotNull @PastOrPresent(message = "Start date should be in the past or present")
        LocalDate startDate,

        @NotNull
        LocalDate endDate
) {}
