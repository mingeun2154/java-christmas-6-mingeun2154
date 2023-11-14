package christmas.domain.event;

import static christmas.domain.event.DiscountEvent.WEEKDAY_DISCOUNT;
import static christmas.domain.event.DiscountEvent.WEEKEND_DISCOUNT;

import christmas.domain.order.Basket;
import christmas.domain.order.VisitDate;
import java.util.EnumMap;

public class BenefitDetails {

    private final EnumMap<DiscountEvent, Integer> discountDetails;
    private final Integer christmasDDayDiscountAmount;
    private final Integer specialDiscountAmount;
    private final Basket gifts;

    public static BenefitDetails of(Basket orders, VisitDate visitDate) {
        return new BenefitDetails(orders, visitDate);
    }

    public int totalBenefitAmount() {
        return discountDetails.entrySet().stream()
                .mapToInt((discount) -> discount.getKey().amount() * discount.getValue())
                .sum()
                + christmasDDayDiscountAmount
                + specialDiscountAmount
                + gifts.totalPriceBeforeDiscount();
    }

    public int christmasDDayDiscountAmount() {
        return this.christmasDDayDiscountAmount;
    }

    public int weekdayDiscountAmount() {
        return WEEKDAY_DISCOUNT.amount() * discountDetails.get(WEEKDAY_DISCOUNT);
    }

    public int weekendDiscountAmount() {
        return WEEKEND_DISCOUNT.amount() * discountDetails.get(WEEKEND_DISCOUNT);
    }

    public int specialDiscountAmount() {
        return this.specialDiscountAmount;
    }

    public int giftAmount() {
        return gifts.totalPriceBeforeDiscount();
    }

    private BenefitDetails(Basket orders, VisitDate visitDate) {
        discountDetails = new EnumMap<>(DiscountEvent.class);
        for (DiscountEvent policy : DiscountEvent.values()) {
            if (policy.matchPeriod(visitDate)) {
                this.discountDetails.put(policy, orders.countItemsDiscountByEvent(policy));
            }
            if (!policy.matchPeriod(visitDate)) {
                this.discountDetails.put(policy, 0);
            }
        }
        this.christmasDDayDiscountAmount = DiscountEvent.christmasDDayDiscountAmount(visitDate);
        this.specialDiscountAmount = DiscountEvent.specialDiscountAmount(visitDate);
        gifts = Gift.grant(orders.totalPriceBeforeDiscount());
    }
}
