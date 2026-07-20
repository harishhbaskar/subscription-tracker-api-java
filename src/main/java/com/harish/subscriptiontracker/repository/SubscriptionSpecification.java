package com.harish.subscriptiontracker.repository;

import com.harish.subscriptiontracker.entity.Subscription;
import com.harish.subscriptiontracker.entity.enums.Category;
import com.harish.subscriptiontracker.entity.enums.Status;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class SubscriptionSpecification {

    public static Specification<Subscription> filterBy(Long userId, String search, Status status, Category category) {
        return Specification.where(hasUserId(userId))
                .and(hasSearchTerm(search))
                .and(hasStatus(status))
                .and(hasCategory(category));
    }

    private static Specification<Subscription> hasUserId(Long userId) {
        return (root, query, cb) -> cb.equal(root.get("user").get("id"), userId);
    }

    private static Specification<Subscription> hasSearchTerm(String search) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(search)) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%");
        };
    }

    private static Specification<Subscription> hasStatus(Status status) {
        return (root, query, cb) -> {
            if (status == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("status"), status);
        };
    }

    private static Specification<Subscription> hasCategory(Category category) {
        return (root, query, cb) -> {
            if (category == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("category"), category);
        };
    }
}
