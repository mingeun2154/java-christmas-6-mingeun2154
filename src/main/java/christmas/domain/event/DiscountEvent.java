package christmas.domain.event;

import static christmas.domain.order.ItemCategory.DESSERT;
import static christmas.domain.order.ItemCategory.MAIN;

import christmas.domain.order.ItemOrder;
import christmas.domain.order.VisitDate;

public class DiscountEvent {

    /**
     * 총 금액의 할인된 금액을 반환한다.
     */
    public static int discount(int totalAmount, VisitDate visitDate) {
        return applyChristmasDDayDiscount(totalAmount, visitDate);
    }

    /**
     * 상품 한 개의 할인된 금액을 반환한다.
     */
    public static int discount(ItemOrder order, VisitDate visitDate) {
        int afterDiscount = order.totalPriceBeforeDiscount();
        if (visitDate.isWeekday())
            afterDiscount = applyWeekdayDiscount(order);
        if (visitDate.isWeekend())
            afterDiscount = applyWeekendDiscount(order);
        return afterDiscount;
    }

    private static int applyChristmasDDayDiscount(int before, VisitDate visitDate) {
        if (visitDate.isChristmasDDay())
            return before - (100 * (visitDate.getDay() - 1) + 1_000);
        return before;
    }

    private static int applyWeekdayDiscount(ItemOrder order) {
        if (order.getCategory() == DESSERT) {
            return order.totalPriceAfterDiscount(2_023);
        }
        return order.totalPriceBeforeDiscount();
    }

    private static int applyWeekendDiscount(ItemOrder order) {
        if (order.getCategory() == MAIN) {
            return order.totalPriceAfterDiscount(2_023);
        }
        return order.totalPriceBeforeDiscount();
    }
}
