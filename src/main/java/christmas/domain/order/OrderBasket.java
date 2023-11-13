package christmas.domain.order;

import christmas.IO.ItemOrderInput;
import christmas.IO.MultipleOrderInput;
import christmas.exceptions.InvalidOrderInputPattern;
import christmas.exceptions.InvalidQuantityException;
import java.util.ArrayList;
import java.util.List;

public class OrderBasket {

    private static final Integer MAX_TOTAL_QUANTITY = 20;
    private final List<ItemOrder> orderedItems;

    private OrderBasket() {
        orderedItems = new ArrayList<>();
    }

    public static OrderBasket of(MultipleOrderInput input)
            throws InvalidOrderInputPattern, InvalidQuantityException {
        OrderBasket result = new OrderBasket();
        for (ItemOrderInput orderInput : input.getOrders()) {
            result.orderedItems.add(ItemOrder.of(orderInput));
        }
        validateTotalQuantity(result);
        return result;
    }

    private static void validateTotalQuantity(OrderBasket orders) throws InvalidQuantityException {
        if (countTotalItemsQuantity(orders) > MAX_TOTAL_QUANTITY)
            throw new InvalidQuantityException();
    }

    private static int countTotalItemsQuantity(OrderBasket orders) {
        int totalQuantity = 0;
        for (ItemOrder item : orders.orderedItems) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }
}
