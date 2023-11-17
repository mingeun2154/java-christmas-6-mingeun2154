package christmas.service;

import christmas.domain.event.BenefitDetails;
import christmas.domain.order.Basket;
import christmas.domain.order.Order;
import christmas.domain.order.VisitDate;

public class ChristmasEventService {

    private final Order order;
    private final BenefitDetails benefits;

    public ChristmasEventService(VisitDate visitDate, Basket basket) {
        this.order = Order.of(basket, visitDate);
        this.benefits = BenefitDetails.of(order);
    }

    public BenefitsPreview benefits() {
        return new BenefitsPreview(order, benefits);
    }

    public int expectedAmountAfterDiscount() {
        return order.totalPriceBeforeDiscount() - Math.abs(benefits.totalBenefitAmount());
    }

}
