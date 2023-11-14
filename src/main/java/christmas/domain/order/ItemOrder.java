package christmas.domain.order;

import christmas.IO.ItemOrderInput;
import christmas.exceptions.InvalidQuantityException;
import christmas.exceptions.NoSuchItemExistsException;

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

    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @return 상품의 한 개당 가격 * 주문 수량
     */
    public int totalPriceBeforeDiscount() {
        return quantity * item.getPrice();
    }

    public int totalPriceAfterDiscount(int discountAmount) {
        return quantity * (item.getPrice() - discountAmount);
    }

    public ItemCategory getCategory() {
        return item.getCategory();
    }

    private static void validateQuantityLowerBound(ItemOrderInput input) throws InvalidQuantityException {
        if (input.getQuantity() < MIN_QUANTITY)
            throw new InvalidQuantityException();
    }
}
