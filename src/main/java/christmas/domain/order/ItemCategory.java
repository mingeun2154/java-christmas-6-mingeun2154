package christmas.domain.order;

public enum ItemCategory {

    APPETIZER("애피타이저"),
    MAIN("메인"),
    DESSERT("디저트"),
    DRINK("음료");

    private final String name;

    private ItemCategory(String name) {
        this.name = name;
    }
}
