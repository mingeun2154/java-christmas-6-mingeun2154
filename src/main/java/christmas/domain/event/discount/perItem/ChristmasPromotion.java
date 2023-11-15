package christmas.domain.event.discount.perItem;

import static christmas.domain.order.ItemCategory.*;
import static java.time.DayOfWeek.*;

import christmas.domain.event.discount.perOrder.DiscountPerOrder;
import christmas.domain.order.ItemCategory;
import christmas.domain.order.VisitDate;
import java.time.DayOfWeek;
import java.util.List;

public enum ChristmasPromotion implements DiscountPerOrder {

    WEEKDAY_DISCOUNT("평일 할인", 2_023, DESSERT, List.of(SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY)),
    WEEKEND_DISCOUNT("주말 할인", 2_023, MAIN, List.of(FRIDAY, SATURDAY));

    private static final Integer SPECIAL_DISCOUNT_AMOUNT = 1_000;

    private final String name;
    private final Integer amount;
    private final ItemCategory targetCategory;
    private final List<DayOfWeek> targetDaysOfWeek;

    @Override
    public int benefitAmount() {
        return amount;
    }

    @Override
    public String getEventName() {
        return this.name;
    }

    public boolean matchPeriod(VisitDate visitDate) {
        return visitDate.matchDaysOfWeek(targetDaysOfWeek);
    }

    public boolean matchCategory(ItemCategory category) {
        return targetCategory == category;
    }

    private ChristmasPromotion(String name, int amount, ItemCategory category, List<DayOfWeek> days) {
        this.name = name;
        this.amount = amount;
        this.targetCategory = category;
        this.targetDaysOfWeek = days;
    }
}
