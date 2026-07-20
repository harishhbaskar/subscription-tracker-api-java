package com.harish.subscriptiontracker.controller;

import com.harish.subscriptiontracker.dto.CreateSubscriptionRequest;
import com.harish.subscriptiontracker.dto.SubscriptionResponse;
import com.harish.subscriptiontracker.response.ApiResponse;
import com.harish.subscriptiontracker.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.harish.subscriptiontracker.entity.enums.Category;
import com.harish.subscriptiontracker.entity.enums.Status;

@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SubscriptionResponse>> createSubscription(
            @Valid @RequestBody CreateSubscriptionRequest request) {
        SubscriptionResponse response = subscriptionService.createSubscription(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Subscription created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<SubscriptionResponse>>> getUserSubscriptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Category category) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<SubscriptionResponse> response = subscriptionService.getUserSubscriptions(pageable, search, status, category);
        return ResponseEntity.ok(ApiResponse.success("Subscriptions retrieved successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubscriptionResponse>> getSubscriptionById(@PathVariable Long id) {
        SubscriptionResponse response = subscriptionService.getSubscriptionById(id);
        return ResponseEntity.ok(ApiResponse.success("Subscription retrieved successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.ok(ApiResponse.success("Subscription deleted successfully", null));
    }
}
