package christmas.domain.order;

import static christmas.domain.order.ItemCategory.DRINK;

import christmas.IO.ItemOrderInput;
import christmas.IO.MultipleOrderInput;
import christmas.exceptions.DrinksOnlyOrderedException;
import christmas.exceptions.InvalidOrderInputPattern;
import christmas.exceptions.InvalidQuantityException;
import java.util.EnumMap;

public class Basket {

    private static final Integer MAX_TOTAL_QUANTITY = 20;
    private final EnumMap<MenuItem, Integer> orders;

    public static Basket of(MultipleOrderInput input)
            throws InvalidOrderInputPattern, InvalidQuantityException, DrinksOnlyOrderedException {
        Basket result = new Basket();
        for (ItemOrderInput orderInput : input.getOrders()) {
            ItemOrder itemOrder = ItemOrder.of(orderInput);
            result.orders.put(itemOrder.getItem(), itemOrder.getQuantity());
        }
        validateTotalQuantity(result);
        validateDrinksOnlyOrder(result);
        return result;
    }

    public int totalPriceBeforeDiscount() {
        return orders.entrySet().stream()
                .mapToInt(order -> order.getKey().getPrice() * order.getValue())
                .sum();
    }

    public void addItem(MenuItem item, int quantity) {
        orders.put(item, quantity);
    }

    public int count(ItemCategory category) {
        return (int) orders.entrySet().stream()
                .filter(order -> order.getKey().matchCategory(category))
                .mapToInt(order -> order.getValue())
                .sum();
    }

    public String itemsView() {
        if (orders.size() == 0) {
            return "없음";
        }
        StringBuilder sb = new StringBuilder();
        orders.entrySet().forEach(order -> {
            sb.append(order.getKey().getName())
                    .append(" ")
                    .append(order.getValue())
                    .append("개\n");
        });
        return sb.toString();
    }

    public Basket() {
        orders = new EnumMap<>(MenuItem.class);
    }

    private static void validateTotalQuantity(Basket orders) throws InvalidQuantityException {
        if (countTotalItemsQuantity(orders) > MAX_TOTAL_QUANTITY) {
            throw new InvalidQuantityException();
        }
    }

    private static void validateDrinksOnlyOrder(Basket basket) throws DrinksOnlyOrderedException {
        if (countTotalItemsQuantity(basket) == basket.count(DRINK)) {
            throw new DrinksOnlyOrderedException();
        }
    }

    private static int countTotalItemsQuantity(Basket basket) {
        return basket.orders.entrySet()
                .stream()
                .mapToInt(order -> order.getValue())
                .sum();
    }
}
