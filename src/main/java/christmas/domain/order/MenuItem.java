package christmas.domain.order;

import static christmas.domain.order.ItemCategory.APPETIZER;
import static christmas.domain.order.ItemCategory.DESSERT;
import static christmas.domain.order.ItemCategory.DRINK;
import static christmas.domain.order.ItemCategory.MAIN;

import christmas.exceptions.NoSuchItemExistsException;
import java.util.Arrays;

public enum MenuItem {

    /**
     * <애피타이저>
     * 양송이수프(6,000), 타파스(5,500), 시저샐러드(8,000)
     *
     * <메인>
     * 티본스테이크(55,000), 바비큐립(54,000), 해산물파스타(35,000), 크리스마스파스타(25,000)
     *
     * <디저트>
     * 초코케이크(15,000), 아이스크림(5,000)
     *
     * <음료>
     * 제로콜라(3,000), 레드와인(60,000), 샴페인(25,000)
     */

    MUSHROOM_SOUP(APPETIZER, "양송이수프", 6_000),
    TAPAS(APPETIZER, "타파스", 5_500),
    CAESAR_SALAD(APPETIZER, "시저샐러드", 8_000),

    T_BONE_STEAK(MAIN, "티본스테이크", 55_000),
    BARBECUE_RIB(MAIN, "바비큐립", 54_000),
    SEAFOOD_PASTA(MAIN, "해산물파스타", 35_000),
    CHRISTMAS_PASTA(MAIN, "크리스마스파스타", 25_000),

    CHOCOLATE_CAKE(DESSERT, "초코케이크", 15_000),
    ICE_CREAM(DESSERT, "아이스크림", 5_000),

    ZERO_COKE(DRINK, "제로콜라", 3_000),
    RED_WINE(DRINK, "레드와인", 60_000),
    CHAMPAGNE(DRINK, "샴페인", 25_000);


    private final ItemCategory itemCategory;
    private final String name;
    private final Integer price;

    public static MenuItem of(String inputName) throws NoSuchItemExistsException {
        return Arrays.stream(values())
                .filter(item -> item.name.equals(inputName))
                .findFirst()
                .orElseThrow(NoSuchItemExistsException::new);
    }

    public ItemCategory getCategory() {
        return itemCategory;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    private MenuItem(ItemCategory itemCategory, String name, Integer price) {
        this.itemCategory = itemCategory;
        this.name = name;
        this.price = price;
    }
}
