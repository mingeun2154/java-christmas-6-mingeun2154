package christmas.IO;

import christmas.exceptions.InvalidOrderInputPattern;
import java.util.ArrayList;
import java.util.List;

public class MultipleOrderInput {

    private static final String SEPERATOR = ",";
    private final List<ItemOrderInput> orders;

    private MultipleOrderInput() {
        orders = new ArrayList<>();
    }

    public static MultipleOrderInput of(String input) throws InvalidOrderInputPattern {
        MultipleOrderInput result = new MultipleOrderInput();
        for (String order : input.split(SEPERATOR)) {
            result.orders.add(ItemOrderInput.of(order));
        }
        return result;
    }

    public List<ItemOrderInput> getOrders() {
        return orders;
    }
}
