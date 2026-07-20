package com.harish.subscriptiontracker.service;

import com.harish.subscriptiontracker.dto.CreateSubscriptionRequest;
import com.harish.subscriptiontracker.dto.SubscriptionResponse;
import com.harish.subscriptiontracker.entity.Subscription;
import com.harish.subscriptiontracker.entity.User;
import com.harish.subscriptiontracker.exception.ResourceNotFoundException;
import com.harish.subscriptiontracker.repository.SubscriptionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public SubscriptionResponse createSubscription(CreateSubscriptionRequest request) {
        Subscription subscription = new Subscription();
        subscription.setUser(getAuthenticatedUser());
        subscription.setName(request.name());
        subscription.setPrice(request.price());
        
        if (request.currency() != null) {
            subscription.setCurrency(request.currency());
        }
        
        subscription.setFrequency(request.frequency());
        subscription.setCategory(request.category());
        subscription.setPaymentMethod(request.paymentMethod());
        subscription.setStartDate(request.startDate());
        subscription.setEndDate(request.endDate());

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return SubscriptionResponse.from(savedSubscription);
    }

    public Page<SubscriptionResponse> getUserSubscriptions(Pageable pageable) {
        User user = getAuthenticatedUser();
        Page<Subscription> subscriptions = subscriptionRepository.findByUserId(user.getId(), pageable);
        return subscriptions.map(SubscriptionResponse::from);
    }

    public SubscriptionResponse getSubscriptionById(Long id) {
        Subscription subscription = getSubscriptionAndVerifyOwnership(id);
        return SubscriptionResponse.from(subscription);
    }

    public void deleteSubscription(Long id) {
        Subscription subscription = getSubscriptionAndVerifyOwnership(id);
        subscriptionRepository.delete(subscription);
    }

    private Subscription getSubscriptionAndVerifyOwnership(Long id) {
        User user = getAuthenticatedUser();
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        if (!subscription.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Subscription not found"); // intentionally vague
        }
        return subscription;
    }
}
