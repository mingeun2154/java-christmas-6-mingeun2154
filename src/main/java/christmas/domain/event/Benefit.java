package christmas.domain.event;

import christmas.domain.order.Basket;
import java.util.function.Predicate;

public interface Benefit {

    String benefitName();

    int benefitAmount();
}
