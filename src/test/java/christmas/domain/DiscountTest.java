package christmas.domain;

import static christmas.domain.order.MenuItem.CHOCOLATE_CAKE;
import static christmas.domain.order.MenuItem.MUSHROOM_SOUP;
import static christmas.domain.order.MenuItem.RED_WINE;
import static christmas.domain.order.MenuItem.SEAFOOD_PASTA;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import christmas.IO.MultipleOrderInput;
import christmas.IO.PureNumber;
import christmas.domain.order.OrderBasket;
import christmas.domain.order.VisitDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DiscountTest {

    @DisplayName("크리스마스 디데이 할인만 적용")
    @ParameterizedTest
    @CsvSource(value = {"1 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2",
            "10 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2",
            "25 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2"}, delimiter = ' ')
    void hitOnlyChristmasDDayEvent(String day, String orderInputs) {
        final Integer totalPriceBeforeDiscount = 5 * MUSHROOM_SOUP.getPrice() + 2 * SEAFOOD_PASTA.getPrice()
                + 5 * CHOCOLATE_CAKE.getPrice() + 2 * RED_WINE.getPrice();
        final Integer expectedDiscountAmount = 100 * (Integer.parseInt(day) - 1) + 1_000;
        final VisitDate visitDate = VisitDate.of(PureNumber.wrap(day));
        final OrderBasket orders = OrderBasket.of(MultipleOrderInput.of(orderInputs));
        assertThat(orders.totalPriceAfterDiscount(visitDate))
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
        assertThat(orders.totalPriceAfterDiscount(visitDate))
                .isEqualTo(totalPriceBeforeDiscount);
    }

    @DisplayName("평일 할인만 적용된 금액 계산")
    @ParameterizedTest
    @CsvSource(value = {"26 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2",
            "27 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2",
            "28 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2"}, delimiter = ' ')
    void hitOnlyWeekDayDiscount(String day, String orderInputs) {
        final Integer totalPriceBeforeDiscount = 5 * MUSHROOM_SOUP.getPrice() + 2 * SEAFOOD_PASTA.getPrice()
                + 5 * CHOCOLATE_CAKE.getPrice() + 2 * RED_WINE.getPrice();
        final Integer discountAmount = 5 * 2_023;
        final VisitDate visitDate = VisitDate.of(PureNumber.wrap(day));
        final OrderBasket orders = OrderBasket.of(MultipleOrderInput.of(orderInputs));
        assertThat(orders.totalPriceAfterDiscount(visitDate))
                .isEqualTo(totalPriceBeforeDiscount - discountAmount);
    }

    @DisplayName("평일 할인 + 크리스마스 디데이 할인이 적용된 금액 계산")
    @ParameterizedTest
    @CsvSource(value = {"4 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2",
            "4 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2",
            "11 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2",
            "14 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2",
            "18 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2",
            "21 양송이수프-5,해산물파스타-2,초코케이크-5,레드와인-2"}, delimiter = ' ')
    void hitChristmasDDayAndWeekDayDiscount(String day, String orderInputs) {
        final Integer totalPriceBeforeDiscount = 5 * MUSHROOM_SOUP.getPrice() + 2 * SEAFOOD_PASTA.getPrice()
                + 5 * CHOCOLATE_CAKE.getPrice() + 2 * RED_WINE.getPrice();
        final Integer discountAmount = 5 * 2_023 + christmasDDayDiscountAmount(day);
        final VisitDate visitDate = VisitDate.of(PureNumber.wrap(day));
        final OrderBasket orders = OrderBasket.of(MultipleOrderInput.of(orderInputs));
        assertThat(orders.totalPriceAfterDiscount(visitDate))
                .isEqualTo(totalPriceBeforeDiscount - discountAmount);
    }

    int christmasDDayDiscountAmount(String day) {
        Integer d = Integer.parseInt(day);
        if (d >= 1 && d <= 25) {
            return 100 * (d - 1) + 1_000;
        }
        return 0;
    }
}