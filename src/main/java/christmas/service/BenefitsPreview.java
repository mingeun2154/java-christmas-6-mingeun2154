package christmas.service;

import christmas.domain.event.BenefitDetails;
import christmas.domain.order.Basket;

public record BenefitsPreview(
        Basket order,
        BenefitDetails benefits
) {
}
