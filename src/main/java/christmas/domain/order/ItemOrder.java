package christmas.domain.order;

import christmas.IO.ItemOrderInput;
import christmas.exceptions.InvalidQuantityException;
import christmas.exceptions.NoSuchItemExistsException;
import java.awt.Menu;
import java.util.EnumMap;

public class ItemOrder {

    private static final Integer MIN_QUANTITY = 1;
    private final MenuItem item;
    private final Integer quantity;

    private ItemOrder(MenuItem item, Integer quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public static ItemOrder of(ItemOrderInput input)
            throws NoSuchItemExistsException, InvalidQuantityException {
        validateQuantityLowerBound(input);
        return new ItemOrder(MenuItem.of(input.getItemName()), input.getQuantity());
    }

    public static ItemOrder of(MenuItem item, int quantity) {
        return new ItemOrder(item, quantity);
    }

    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @return 상품의 한 개당 가격 * 주문 수량
     */
    public int totalPriceBeforeDiscount() {
        return quantity * item.getPrice();
    }

    public MenuItem getItem() {
        return item;
    }

    private static void validateQuantityLowerBound(ItemOrderInput input) throws InvalidQuantityException {
        if (input.getQuantity() < MIN_QUANTITY)
            throw new InvalidQuantityException();
    }
}
