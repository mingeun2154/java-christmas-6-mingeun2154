package christmas.domain.event;

import static christmas.domain.order.ItemCategory.DESSERT;
import static christmas.domain.order.ItemCategory.MAIN;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;

import christmas.domain.order.ItemCategory;
import christmas.domain.order.VisitDate;
import java.time.DayOfWeek;
import java.util.List;

public enum DiscountEvent {

    WEEKDAY_DISCOUNT("평일 할인", 2_023, DESSERT, List.of(SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY)),
    WEEKEND_DISCOUNT("주말 할인", 2_023, MAIN, List.of(FRIDAY, SATURDAY));

    private static final Integer SPECIAL_DISCOUNT_AMOUNT = 1_000;

    private final String name;
    private final Integer amount;
    private final ItemCategory targetCategory;
    private final List<DayOfWeek> targetDays;

    public int amount() {
        return amount;
    }

    public boolean matchPeriod(VisitDate visitDate) {
        return targetDays.contains(visitDate.getDayOfWeek());
    }

    public boolean matchCategory(ItemCategory category) {
        return targetCategory == category;
    }

    public static int christmasDDayDiscountAmount(VisitDate visitDate) {
        if (visitDate.isChristmasDDay()) {
            return (100 * (visitDate.getDayOfMonth() - 1) + 1_000);
        }
        return 0;
    }

    public static int specialDiscountAmount(VisitDate visitDate) {
        if (visitDate.isSpecialDay()) {
            return SPECIAL_DISCOUNT_AMOUNT;
        }
        return 0;
    }

    public String getName() {
        return this.name;
    }

    private DiscountEvent(String name, int amount, ItemCategory category, List<DayOfWeek> days) {
        this.name = name;
        this.amount = amount;
        this.targetCategory = category;
        this.targetDays = days;
    }
}
