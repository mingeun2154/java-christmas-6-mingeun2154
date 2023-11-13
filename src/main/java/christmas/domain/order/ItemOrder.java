package christmas.domain.order;

import christmas.IO.ItemOrderInput;
import christmas.exceptions.NoSuchItemExistsException;

public class ItemOrder {

    private final MenuItem item;
    private final Integer quantity;

    private ItemOrder(MenuItem item, Integer quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public static ItemOrder of(ItemOrderInput input) throws NoSuchItemExistsException {
        return new ItemOrder(MenuItem.of(input.getItemName()), input.getQuantity());
    }
}
