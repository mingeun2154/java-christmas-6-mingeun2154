package christmas.domain.event;

import static christmas.domain.order.ItemCategory.DESSERT;
import static christmas.domain.order.ItemCategory.MAIN;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;

import christmas.domain.order.Order;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public enum DiscountEvent implements Benefit {


    WEEKDAY_DISCOUNT("평일 할인", visitDate -> isWeekday(visitDate),
            order -> order.count(DESSERT) * 2_023),
    WEEKEND_DISCOUNT("주말 할인", visitDate -> isWeekend(visitDate),
            order -> order.count(MAIN) * 2_023),
    CHRISTMAS_D_DAY_DISCOUNT("크리스마스 디데이 할인", visitDate -> isBeforeChristmas(visitDate),
            order -> order.applyLinearExpressionOnDay((day) -> 100 * (day - 1) + 1_000)),
    SPECIAL_DISCOUNT("특별 할인", visitDate -> isSpecialDate(visitDate),
            (order) -> 1_000);

    private static final int THRESHOLD = 10_000;
    private final String name;
    private final Predicate<Order> activePeriodCondition;
    private final Function<Order, Integer> benefitAmountCalculator;

    DiscountEvent(String name, Predicate<Order> activePeriodCondition,
                  Function<Order, Integer> benefitAmountCalculator) {
        this.name = name;
        this.activePeriodCondition = activePeriodCondition;
        this.benefitAmountCalculator = benefitAmountCalculator;
    }

    @Override
    public String benefitName() {
        return name;
    }

    @Override
    public int benefitAmount(Order order) {
        if (activePeriodCondition.test(order) && order.totalPriceBeforeDiscount() >= THRESHOLD) {
            return benefitAmountCalculator.apply(order);
        }
        return 0;
    }

    private static boolean isWeekday(Order order) {
        return !isWeekend(order);
    }

    private static boolean isWeekend(Order order) {
        return order.matchDaysOfWeek(List.of(FRIDAY, SATURDAY));
    }

    private static boolean isBeforeChristmas(Order order) {
        return order.matchDaysOfMonth(1, 25);
    }

    private static boolean isSpecialDate(Order order) {
        return order.matchDaysOfMonth(List.of(3, 10, 17, 24, 25, 31));
    }

}
