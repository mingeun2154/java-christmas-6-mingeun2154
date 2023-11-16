package christmas.domain.event;

import christmas.domain.event.discount.perItem.ChristmasPromotion;
import christmas.domain.event.discount.perOrder.ChristmasDDayDiscount;
import christmas.domain.event.discount.perOrder.Discount;
import christmas.domain.event.discount.perOrder.DiscountPerOrder;
import christmas.domain.event.discount.perOrder.SpecialDiscount;
import christmas.domain.order.Basket;
import christmas.domain.order.VisitDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

public class BenefitDetails {

    private final EnumMap<ChristmasPromotion, Integer> perItemDiscounts;
    private final List<Discount> perOrderDiscounts;
    private final Basket gifts;

    public static BenefitDetails of(Basket orders, VisitDate visitDate) {
        return new BenefitDetails(orders, visitDate);
    }

    public int totalBenefitAmount() {
        return totalBenefitAmountByDiscountPerItem()
                + totalBenefitAmountByDiscountPerOrder()
                + totalBenefitAmountByGifts();
    }

    public int discountAmountBy(ChristmasPromotion discountEvent) {
        return discountEvent.benefitAmount() * perItemDiscounts.get(discountEvent);
    }

    public int discountAmountBy(Class<? extends Discount> discountType) {
        return perOrderDiscounts.stream().filter(
                discount -> discount.getClass().equals(discountType))
                .findAny()
                .get()
                .benefitAmount();
    }

    public int giftAmount() {
        return gifts.totalPriceBeforeDiscount();
    }

    public String detailsView() {
        if (totalBenefitAmount() == 0) {
            return "없음";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("크리스마스 디데이 할인: -%,d원\n", discountAmountBy(ChristmasDDayDiscount.class)));
        for (Entry<ChristmasPromotion, Integer> detail : perItemDiscounts.entrySet()) {
            sb.append(String.format("%s: -%,d원\n",
                    detail.getKey().benefitName(),
                    detail.getKey().benefitAmount() * detail.getValue()));
        }
        sb.append(String.format("증정 이벤트: -%,d원\n", gifts.totalPriceBeforeDiscount()));
        return sb.toString();
    }

    private BenefitDetails(Basket orders, VisitDate visitDate) {
        perItemDiscounts = new EnumMap<>(ChristmasPromotion.class);
        for (ChristmasPromotion policy : ChristmasPromotion.values()) {
            if (policy.matchPeriod(visitDate)) {
                this.perItemDiscounts.put(policy, orders.countItemsDiscountByEvent(policy));
            }
            if (!policy.matchPeriod(visitDate)) {
                this.perItemDiscounts.put(policy, 0);
            }
        }
        perOrderDiscounts = new ArrayList<>();
        perOrderDiscounts.add(ChristmasDDayDiscount.of(visitDate));
        perOrderDiscounts.add(SpecialDiscount.of(visitDate));
        gifts = Gift.of(orders.totalPriceBeforeDiscount());
    }

    private int totalBenefitAmountByDiscountPerOrder() {
        return perOrderDiscounts.stream()
                .mapToInt(discount -> discount.benefitAmount())
                .sum();
    }

    private int totalBenefitAmountByDiscountPerItem() {
        return perItemDiscounts.entrySet().stream()
                .mapToInt((discount) -> discount.getKey().benefitAmount() * discount.getValue())
                .sum();
    }

    private int totalBenefitAmountByGifts() {
        return gifts.totalPriceBeforeDiscount();
    }
}
