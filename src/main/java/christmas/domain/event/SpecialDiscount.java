package christmas.domain.event;

import christmas.domain.order.VisitDate;
import java.util.List;

public class SpecialDiscount extends Discount implements DiscountPerOrder {

    private static final String EVENT_NAME = "크리스마스 디데이 할인";
    private static final List<Integer> EVENT_DAYS_OF_MONTH = List.of(3, 10, 17, 24, 25, 31);

    public static SpecialDiscount of(VisitDate visitDate) {
        if (inEventPeriod(visitDate)) {
            return new SpecialDiscount(1_000);
        }
        return new SpecialDiscount();
    }

    @Override
    public int benefitAmount() {
        return amount;
    }

    private SpecialDiscount(int amount) {
        super(amount, EVENT_NAME);
    }

    private SpecialDiscount() {
        super(0, EVENT_NAME);
    }

    private static final boolean inEventPeriod(VisitDate visitDate) {
        return EVENT_DAYS_OF_MONTH.contains(visitDate.getDayOfMonth());
    }
}
