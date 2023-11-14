package christmas.domain.order;

import static christmas.domain.order.ItemCategory.DRINK;

import christmas.IO.ItemOrderInput;
import christmas.IO.MultipleOrderInput;
import christmas.domain.event.DiscountEvent;
import christmas.exceptions.DrinksOnlyOrderedException;
import christmas.exceptions.InvalidOrderInputPattern;
import christmas.exceptions.InvalidQuantityException;
import java.util.ArrayList;
import java.util.List;

public class Basket {

    private static final Integer MAX_TOTAL_QUANTITY = 20;
    private final List<ItemOrder> orderedItems;

    public static Basket createEmptyBasket() {
        return new Basket();
    }

    public static Basket of(MultipleOrderInput input)
            throws InvalidOrderInputPattern, InvalidQuantityException, DrinksOnlyOrderedException {
        Basket result = new Basket();
        for (ItemOrderInput orderInput : input.getOrders()) {
            result.orderedItems.add(ItemOrder.of(orderInput));
        }
        validateTotalQuantity(result);
        validateDrinksOnlyOrder(result);
        return result;
    }

    public int totalPriceBeforeDiscount() {
        return orderedItems.stream().mapToInt(ItemOrder::totalPriceBeforeDiscount).sum();
    }

    public void addItem(MenuItem item, int quantity) {
        orderedItems.add(ItemOrder.of(item, quantity));
    }

    public int countItemsDiscountByEvent(DiscountEvent policy) {
        return orderedItems.stream()
                .filter((order) -> policy.matchCategory(order.getCategory()))
                .mapToInt((order) -> order.getQuantity())
                .sum();
    }

    public String itemsView() {
        if (orderedItems.size() == 0) {
            return "없음";
        }
        StringBuilder sb = new StringBuilder();
        orderedItems.forEach(item -> {
            sb.append(item.getItemName())
                    .append(" ")
                    .append(item.getQuantity())
                    .append("개\n");
        });
        return sb.toString();
    }

    private Basket() {
        orderedItems = new ArrayList<>();
    }

    private static void validateTotalQuantity(Basket orders) throws InvalidQuantityException {
        if (countTotalItemsQuantity(orders) > MAX_TOTAL_QUANTITY) {
            throw new InvalidQuantityException();
        }
    }

    private static void validateDrinksOnlyOrder(Basket orders) throws DrinksOnlyOrderedException {
        for (ItemOrder item : orders.orderedItems) {
            if (item.getCategory() != DRINK) {
                return;
            }
        }
        throw new DrinksOnlyOrderedException();
    }

    private static int countTotalItemsQuantity(Basket orders) {
        int totalQuantity = 0;
        for (ItemOrder item : orders.orderedItems) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }
}
