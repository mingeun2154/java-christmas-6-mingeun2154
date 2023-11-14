package christmas.domain.event;

import christmas.domain.order.VisitDate;

public class DiscountEvent {

    /**
     * 총 금액의 할인된 금액을 반환한다.
     */
    public static int discount(int totalAmount, VisitDate visitDate) {
        return applyChristmasDDayDiscount(totalAmount, visitDate);
    }

    private static int applyChristmasDDayDiscount(int before, VisitDate visitDate) {
        if (visitDate.isChristmasDDay())
            return before - (100 * (visitDate.getDay() - 1) + 1_000);
        return before;
    }
}
