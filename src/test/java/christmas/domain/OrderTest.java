package christmas.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import christmas.IO.ItemOrderInput;
import christmas.IO.MultipleOrderInput;
import christmas.domain.order.ItemOrder;
import christmas.domain.order.OrderBasket;
import christmas.exceptions.DrinksOnlyOrderedException;
import christmas.exceptions.DuplicateItemInOrderException;
import christmas.exceptions.InvalidQuantityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class OrderTest {

    @DisplayName("메뉴에 없는 상품 주문 -> 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"abc-1", "자바-2", "양동이수프-3", "티본스테이크"})
    void invalidOrderItem(String input) {
        assertThatThrownBy(
                () -> ItemOrder.of(ItemOrderInput.of(input))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 존재하는 상품 주문")
    @ParameterizedTest
    @ValueSource(strings = {"양송이수프-3", "크리스마스파스타-20", "초코케이크-10", "샴페인-1"})
    void validOrderItem(String input) {
        Assertions.assertDoesNotThrow(
                () -> ItemOrder.of(ItemOrderInput.of(input))
        );
    }

    @DisplayName("유효하지 않은 주문 수량 -> 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"양송이수프-3,타파스-21", "초코케이크-10,아이스크림-10,양송이수프-1", "티본스테이크-0,샴페인-2"})
    void invalidOrderQuantity(String input) {
        assertThatThrownBy(
                () -> OrderBasket.of(MultipleOrderInput.of(input))
        ).isInstanceOf(InvalidQuantityException.class);
    }

    @DisplayName("유효한 주문 수량")
    @ParameterizedTest
    @ValueSource(strings = {"양송이수프-3,타파스-17", "초코케이크-10,아이스크림-10", "티본스테이크-20"})
    void validOrderQuantity(String input) {
        Assertions.assertDoesNotThrow(
                () -> OrderBasket.of(MultipleOrderInput.of(input))
        );
    }

    @DisplayName("음료만 주문한 경우 -> 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"제로콜라-20", "제로콜라-1", "제로콜라-1,레드와인-1,샴페인-10"})
    void youCannotOrderOnlyDrinks(String input) {
        assertThatThrownBy(
                () -> OrderBasket.of(MultipleOrderInput.of(input))
        ).isInstanceOf(DrinksOnlyOrderedException.class);
    }

    @DisplayName("동일한 상품을 2번 이상 주문 -> 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"초코케이크-19,초코케이크-1", "크리스마스파스타-2,제로콜라-4,크리스마스파스타-2"})
    void duplicateMenuItemOrder(String input) {
        assertThatThrownBy(
                () -> OrderBasket.of(MultipleOrderInput.of(input))
        ).isInstanceOf(DuplicateItemInOrderException.class);
    }
}