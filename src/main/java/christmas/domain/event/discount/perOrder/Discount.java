package christmas.domain.event.discount.perOrder;

public abstract class Discount implements DiscountPerOrder {

    protected final Integer amount;
    private final String eventName;

    public Discount(Integer amount, String eventName) {
        this.amount = amount;
        this.eventName = eventName;
    }

    @Override
    public String getEventName() {
        return eventName;
    }
}
