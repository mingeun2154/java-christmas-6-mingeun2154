package christmas.domain;

import static christmas.domain.order.MenuItem.CHOCOLATE_CAKE;
import static christmas.domain.order.MenuItem.MUSHROOM_SOUP;
import static christmas.domain.order.MenuItem.RED_WINE;
import static christmas.domain.order.MenuItem.SEAFOOD_PASTA;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import christmas.IO.MultipleOrderInput;
import christmas.domain.order.Basket;
import christmas.exceptions.DrinksOnlyOrderedException;
import christmas.exceptions.DuplicateItemInOrderException;
import christmas.exceptions.InvalidQuantityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BasketTest {

    @DisplayName("유효하지 않은 주문 수량 -> 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"양송이수프-3,타파스-21", "초코케이크-10,아이스크림-10,양송이수프-1", "티본스테이크-0,샴페인-2"})
    void invalidOrderQuantity(String input) {
        assertThatThrownBy(
                () -> Basket.of(MultipleOrderInput.of(input))
        ).isInstanceOf(InvalidQuantityException.class);
    }

    @DisplayName("유효한 주문 수량")
    @ParameterizedTest
    @ValueSource(strings = {"양송이수프-3,타파스-17", "초코케이크-10,아이스크림-10", "티본스테이크-20"})
    void validOrderQuantity(String input) {
        Assertions.assertDoesNotThrow(
                () -> Basket.of(MultipleOrderInput.of(input))
        );
    }

    @DisplayName("음료만 주문한 경우 -> 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"제로콜라-20", "제로콜라-1", "제로콜라-1,레드와인-1,샴페인-10"})
    void youCannotOrderOnlyDrinks(String input) {
        assertThatThrownBy(
                () -> Basket.of(MultipleOrderInput.of(input))
        ).isInstanceOf(DrinksOnlyOrderedException.class);
    }

    @DisplayName("동일한 상품을 2번 이상 주문 -> 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"초코케이크-19,초코케이크-1", "크리스마스파스타-2,제로콜라-4,크리스마스파스타-2"})
    void duplicateMenuItemOrder(String input) {
        assertThatThrownBy(
                () -> Basket.of(MultipleOrderInput.of(input))
        ).isInstanceOf(DuplicateItemInOrderException.class);
    }

    @DisplayName("주문한 상품들의 가격 총합을 계산")
    @ParameterizedTest
    @ValueSource(strings = {"양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2"})
    void calculateTotalPriceWithOutDiscount(String input) {
        Basket orders = Basket.of(MultipleOrderInput.of(input));
        int expectedPrice = 5 * MUSHROOM_SOUP.getPrice() + 2 * SEAFOOD_PASTA.getPrice()
                + 5 * CHOCOLATE_CAKE.getPrice() + 2 * RED_WINE.getPrice();
        assertThat(orders.totalPriceBeforeDiscount()).isEqualTo(expectedPrice);
    }
}
