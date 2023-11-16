package christmas.domain.event.discount.perOrder;

import christmas.domain.event.Benefit;

public abstract class Discount implements Benefit {

    protected final Integer amount;
    private final String eventName;

    public Discount(Integer amount, String eventName) {
        this.amount = amount;
        this.eventName = eventName;
    }

    @Override
    public int benefitAmount() {
        return amount;
    }

    @Override
    public String benefitName() {
        return eventName;
    }
}
