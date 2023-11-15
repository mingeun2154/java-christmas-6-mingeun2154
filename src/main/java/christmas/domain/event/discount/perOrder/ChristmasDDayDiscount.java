package christmas.domain.event.discount.perOrder;

import christmas.domain.order.VisitDate;
import java.util.function.IntUnaryOperator;

public class ChristmasDDayDiscount extends Discount implements DiscountPerOrder {

    private static final Integer EVENT_START_DAY_OF_MONTH_INCLUDE = 1;
    private static final Integer EVENT_END_DAY_OF_MONTH_INCLUDE = 25;
    private static final String EVENT_NAME = "크리스마스 디데이 할인";
    private static final IntUnaryOperator BENEFIT_EXPRESSION = (d) -> 100 * (d - 1) + 1_000;

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
        return visitDate.applyLinearExpressionOnDayOfMonth(BENEFIT_EXPRESSION);
    }

    private static boolean inEventPeriod(VisitDate visitDate) {
        return visitDate.matchDaysOfMonth(EVENT_START_DAY_OF_MONTH_INCLUDE, EVENT_END_DAY_OF_MONTH_INCLUDE);
    }
}
