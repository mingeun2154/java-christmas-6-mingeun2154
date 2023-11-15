package christmas.domain.event.discount.perOrder;

import christmas.domain.order.VisitDate;

public class ChristmasDDayDiscount extends Discount implements DiscountPerOrder {

    private static final Integer EVENT_START_DAY_OF_MONTH_INCLUDE = 1;
    private static final Integer EVENT_END_DAY_OF_MONTH_INCLUDE = 25;
    private static final String EVENT_NAME = "크리스마스 디데이 할인";

    @Override
    public int benefitAmount() {
        return amount;
    }

    public static ChristmasDDayDiscount of(VisitDate visitDate) {
        if (inEventPeriod(visitDate)) {
            return new ChristmasDDayDiscount(calculateBenefitAmount(visitDate));
        }
        return new ChristmasDDayDiscount();
    }

    private ChristmasDDayDiscount() {
        super(0, EVENT_NAME);
    }

    private ChristmasDDayDiscount(int amount) {
        super(amount, EVENT_NAME);
    }



    private static int calculateBenefitAmount(VisitDate visitDate) {
        return 100 * (visitDate.getDayOfMonth() - 1) + 1_000;
    }

    private static boolean inEventPeriod(VisitDate visitDate) {
        return (visitDate.getDayOfMonth() >= EVENT_START_DAY_OF_MONTH_INCLUDE
                && visitDate.getDayOfMonth() <= EVENT_END_DAY_OF_MONTH_INCLUDE);
    }
}
