package christmas.domain;

import static christmas.domain.order.MenuItem.CHAMPAGNE;

import christmas.IO.MultipleOrderInput;
import christmas.domain.event.Gift;
import christmas.domain.order.Basket;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class GiftTest {

    @DisplayName("총주문 금액이 12만원 이상인 경우 샴페인 1개 증정")
    @ParameterizedTest
    @CsvSource(value = {"티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1 해산물파스타-2"}, delimiter = ' ')
    void grantChampagne(String giftOrder, String noGiftOrder) {
        Gift gift = new Gift();
        Basket giftOrders = Basket.of(MultipleOrderInput.of(giftOrder));
        Basket noGiftOrders = Basket.of(MultipleOrderInput.of(noGiftOrder));
        Assertions.assertThat(Gift.grant(giftOrders.totalPriceBeforeDiscount())
                        .totalPriceBeforeDiscount())
                .isEqualTo(CHAMPAGNE.getPrice());
        Assertions.assertThat(Gift.grant(noGiftOrders.totalPriceBeforeDiscount())
                        .totalPriceBeforeDiscount())
                .isEqualTo(0);
    }
}
