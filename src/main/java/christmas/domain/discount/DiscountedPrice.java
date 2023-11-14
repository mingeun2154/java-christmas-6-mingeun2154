package christmas.domain.discount;

import christmas.domain.order.OrderBasket;
import christmas.domain.order.VisitDate;

public class DiscountedPrice {

    private final Integer price;

    private DiscountedPrice(Integer price) {
        this.price = price;
    }

    public static DiscountedPrice of(OrderBasket orders, VisitDate visitDate) {
        return new DiscountedPrice(orders.getTotalPrice() - christmasDDayDiscountAmount(visitDate));
    }

    public int getPrice() {
        return price;
    }

    private static int christmasDDayDiscountAmount(VisitDate visitDate) {
        if (visitDate.getDay() >= 1 && visitDate.getDay() <= 25) {
            return (100 * (visitDate.getDay() - 1) + 1_000);
        }
        return 0;
    }
}
