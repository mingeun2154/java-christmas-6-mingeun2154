package christmas.domain;

import static christmas.domain.order.MenuItem.CHOCOLATE_CAKE;
import static christmas.domain.order.MenuItem.MUSHROOM_SOUP;
import static christmas.domain.order.MenuItem.RED_WINE;
import static christmas.domain.order.MenuItem.SEAFOOD_PASTA;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import christmas.IO.MultipleOrderInput;
import christmas.IO.PureNumber;
import christmas.domain.discount.DiscountedPrice;
import christmas.domain.order.OrderBasket;
import christmas.domain.order.VisitDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DiscountPolicyTest {

    /**
     * <애피타이저>
     * 양송이수프(6,000), 타파스(5,500), 시저샐러드(8,000)
     * <메인>
     * 티본스테이크(55,000), 바비큐립(54,000), 해산물파스타(35,000), 크리스마스파스타(25,000)
     * <디저트>
     * 초코케이크(15,000), 아이스크림(5,000)
     * <음료>
     * 제로콜라(3,000), 레드와인(60,000), 샴페인(25,000)
     */

    @DisplayName("크리스마스 디데이 할인 기간에 방문")
    @ParameterizedTest
    @CsvSource(value = {"1 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2",
            "10 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2",
            "25 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2"}, delimiter = ' ')
    void hitChristmasDDayEvent(String day, String orderInputs) {
        final Integer totalPriceBeforeDiscount = 5 * MUSHROOM_SOUP.getPrice() + 2 * SEAFOOD_PASTA.getPrice()
                + 5 * CHOCOLATE_CAKE.getPrice() + 2 * RED_WINE.getPrice();
        final Integer expectedDiscountAmount = 100 * (Integer.parseInt(day) - 1) + 1_000;
        final VisitDate visitDate = VisitDate.of(PureNumber.wrap(day));
        final OrderBasket orders = OrderBasket.of(MultipleOrderInput.of(orderInputs));
        assertThat(DiscountedPrice.of(orders, visitDate).getPrice())
                .isEqualTo(totalPriceBeforeDiscount - expectedDiscountAmount);
    }

    @DisplayName("크리스마스 디데이 할인 기간이 아닌 날짜에 방문")
    @ParameterizedTest
    @CsvSource(value = {"26 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2",
            "28 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2",
            "31 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2"}, delimiter = ' ')
    void missChristmasDDayEvent(String day, String orderInputs) {
        final Integer totalPriceBeforeDiscount = 5 * MUSHROOM_SOUP.getPrice() + 2 * SEAFOOD_PASTA.getPrice()
                + 5 * CHOCOLATE_CAKE.getPrice() + 2 * RED_WINE.getPrice();
        final VisitDate visitDate = VisitDate.of(PureNumber.wrap(day));
        final OrderBasket orders = OrderBasket.of(MultipleOrderInput.of(orderInputs));
        assertThat(DiscountedPrice.of(orders, visitDate).getPrice())
                .isEqualTo(totalPriceBeforeDiscount);
    }
}
