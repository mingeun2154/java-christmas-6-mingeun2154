package christmas.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import christmas.IO.ItemOrderInput;
import christmas.domain.order.ItemOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ItemOrderTest {

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
}