package christmas.domain.event;

import static christmas.domain.order.MenuItem.CHAMPAGNE;

import christmas.domain.order.Basket;

public class Gift {

    private static final Integer CHAMPAGNE_THRESHOLD = 120_000;

    public static Basket grant(int totalPriceBeforeDiscount) {
        Basket gifts = Basket.createEmptyBasket();
        if (totalPriceBeforeDiscount >= CHAMPAGNE_THRESHOLD) {
            gifts.addItem(CHAMPAGNE, 1);
        }
        return gifts;
    }
}
