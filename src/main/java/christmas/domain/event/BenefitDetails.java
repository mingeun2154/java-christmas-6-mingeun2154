package christmas.domain.event;

import christmas.domain.order.Basket;
import christmas.domain.order.Order;
import java.util.EnumMap;
import java.util.List;

public class BenefitDetails {

    private static final List<DiscountEvent> ONGOING_DISCOUNT_EVENTS = List.of(DiscountEvent.values());
    private final EnumMap<DiscountEvent, Integer> appliedDiscountEvents;
    private final Basket gifts;

    public static BenefitDetails of(Order order) {
        return new BenefitDetails(order);
    }

    public int totalBenefitAmount() {
        return benefitAmountByDiscountEvents() + benefitAmountByGifts();
    }

    public int giftAmount() {
        return gifts.totalPriceBeforeDiscount();
    }

    public String detailsView() {
        if (totalBenefitAmount() == 0) {
            return "없음";
        }
        StringBuilder sb = new StringBuilder();
        appliedDiscountEvents.entrySet().forEach((discount) -> {
            if (discount.getValue() != 0) {
                sb.append(String.format("%s %,d원\n", discount.getKey().benefitName(), discount.getValue()));
            }
        });
        sb.append(String.format("증정 이벤트: -%,d원\n", gifts.totalPriceBeforeDiscount()));
        return sb.toString();
    }



    private BenefitDetails(Order order) {
        appliedDiscountEvents = new EnumMap(DiscountEvent.class);
        ONGOING_DISCOUNT_EVENTS.stream().forEach(event -> {
            appliedDiscountEvents.put(event, -1 * event.benefitAmount(order));
        });
        gifts = Gift.of(order.totalPriceBeforeDiscount());
    }

    private int benefitAmountByGifts() {
        return -1 * gifts.totalPriceBeforeDiscount();
    }

    private int benefitAmountByDiscountEvents() {
        return appliedDiscountEvents.entrySet().stream()
                .mapToInt(activeDiscountEvent -> activeDiscountEvent.getValue())
                .sum();
    }

    public int discountAmountBy(DiscountEvent discountEvent) {
        return appliedDiscountEvents.get(discountEvent);
    }
}
