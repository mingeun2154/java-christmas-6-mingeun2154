package christmas.domain.event;

import christmas.domain.order.Order;

public interface Benefit {

    String benefitName();

    int benefitAmount(Order order);
}
