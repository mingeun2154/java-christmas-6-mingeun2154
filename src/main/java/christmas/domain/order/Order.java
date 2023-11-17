package christmas.domain.order;

import java.time.DayOfWeek;
import java.util.List;
import java.util.function.IntUnaryOperator;

public class Order {

    private final Basket basket;

    private final VisitDate orderDate;

    public static Order of(Basket basket, VisitDate visitDate) {
        return new Order(basket, visitDate);
    }

    public boolean matchDaysOfWeek(List<DayOfWeek> days) {
        return orderDate.matchDaysOfWeek(days);
    }

    public boolean matchDaysOfMonth(int startInclude, int endInclude) {
        return orderDate.matchDaysOfMonth(startInclude, endInclude);
    }

    public int count(ItemCategory category) {
        return basket.count(category);
    }

    public int applyLinearExpressionOnDay(IntUnaryOperator expression) {
        return orderDate.applyLinearExpressionOnDayOfMonth(expression);
    }

    private Order(Basket basket, VisitDate orderDate) {
        this.basket = basket;
        this.orderDate = orderDate;
    }

    public int totalPriceBeforeDiscount() {
        return basket.totalPriceBeforeDiscount();
    }

    public boolean matchDaysOfMonth(List<Integer> daysOfMonth) {
        return orderDate.matchDaysOfMonth(daysOfMonth);
    }

    public String itemsView() {
        return basket.itemsView();
    }
}
