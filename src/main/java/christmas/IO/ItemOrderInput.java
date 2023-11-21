package christmas.IO;

import christmas.exceptions.InvalidOrderInputPattern;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemOrderInput {

    private static final Pattern PATTERN = Pattern.compile("^[가-힣]+-\\d+$");

    private final String itemName;
    private final Integer quantity;

    private ItemOrderInput(String itemName, Integer quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public static ItemOrderInput of(String input) throws InvalidOrderInputPattern {
        validateInputPattern(input);
        String[] tokens = input.split("-");
        return new ItemOrderInput(tokens[0], Integer.parseInt(tokens[1]));
    }

    public static void validateInputPattern(String input) throws InvalidOrderInputPattern {
        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new InvalidOrderInputPattern();
        }
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != ItemOrderInput.class) {
            return false;
        }
        ItemOrderInput o = (ItemOrderInput) obj;
        return Objects.equals(o.itemName, itemName);
    }
}
