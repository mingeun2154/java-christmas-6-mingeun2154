package christmas.domain.event;

public abstract class Discount {

    protected final Integer amount;
    private final String eventName;

    public Discount(Integer amount, String eventName) {
        this.amount = amount;
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
