package christmas.service;

import christmas.domain.event.BenefitDetails;
import christmas.domain.order.Basket;
import christmas.domain.order.Order;

public record BenefitsPreview(
        Order order,
        BenefitDetails benefits
) {
}
