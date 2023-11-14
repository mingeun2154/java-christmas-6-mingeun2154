package christmas.domain;

import static christmas.domain.event.DiscountEvent.WEEKDAY_DISCOUNT;
import static christmas.domain.event.DiscountEvent.WEEKEND_DISCOUNT;
import static christmas.domain.order.MenuItem.CHOCOLATE_CAKE;
import static christmas.domain.order.MenuItem.CHRISTMAS_PASTA;
import static christmas.domain.order.MenuItem.MUSHROOM_SOUP;
import static christmas.domain.order.MenuItem.ZERO_COKE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import christmas.IO.MultipleOrderInput;
import christmas.IO.PureNumber;
import christmas.domain.event.BenefitDetails;
import christmas.domain.order.Basket;
import christmas.domain.order.VisitDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DiscountTest {

    static final Integer APPETIZER_COUNT = 1;
    static final Integer MAIN_COUNT = 2;
    static final Integer DESSERT_COUNT = 2;
    static final Integer DRINKS_COUNT = 5;
    static final Integer SPECIAL_DISCOUNT = 1_000;
    static final String order = "양송이수프-1,크리스마스파스타-2,초코케이크-2,제로콜라-5";
    static final Integer totalPriceBeforeDiscount = APPETIZER_COUNT * MUSHROOM_SOUP.getPrice()
            + MAIN_COUNT * CHRISTMAS_PASTA.getPrice()
            + DESSERT_COUNT * CHOCOLATE_CAKE.getPrice()
            + DRINKS_COUNT * ZERO_COKE.getPrice();

    @DisplayName("평일 할인만 적용된 금액 계산")
    @ParameterizedTest
    @CsvSource(value = {"26 " + order, "27 " + order, "28 " + order}, delimiter = ' ')
    void hitOnlyWeekDayDiscount(String day, String orderInputs) {
        final Integer discountAmount = DESSERT_COUNT * WEEKDAY_DISCOUNT.amount();
        final VisitDate visitDate = VisitDate.of(PureNumber.wrap(day));
        final Basket orders = Basket.of(MultipleOrderInput.of(orderInputs));
        assertThat(orders.totalPriceBeforeDiscount() - BenefitDetails.of(orders, visitDate).totalBenefitAmount())
                .isEqualTo(totalPriceBeforeDiscount - discountAmount);
    }

    @DisplayName("평일 할인 + 크리스마스 디데이 할인이 적용된 금액 계산")
    @ParameterizedTest
    @CsvSource(value = {"4 " + order, "4 " + order, "11 " + order, "14 " + order, "18 " + order, "21 " + order},
            delimiter = ' ')
    void hitChristmasDDayAndWeekDayDiscount(String day, String orderInputs) {
        final Integer discountAmount = DESSERT_COUNT * WEEKDAY_DISCOUNT.amount() + christmasDDayDiscountAmount(day);
        final VisitDate visitDate = VisitDate.of(PureNumber.wrap(day));
        final Basket orders = Basket.of(MultipleOrderInput.of(orderInputs));
        assertThat(orders.totalPriceBeforeDiscount() - BenefitDetails.of(orders, visitDate).totalBenefitAmount())
                .isEqualTo(totalPriceBeforeDiscount - discountAmount);
    }

    @DisplayName("주말 할인만 적용된 금액 계산")
    @ParameterizedTest
    @CsvSource(value = {"29 " + order, "30 " + order}, delimiter = ' ')
    void hitOnlyWeekendDiscount(String day, String orderInputs) {
        final Integer discountAmount = MAIN_COUNT * WEEKEND_DISCOUNT.amount();
        final VisitDate visitDate = VisitDate.of(PureNumber.wrap(day));
        final Basket orders = Basket.of(MultipleOrderInput.of(orderInputs));
        assertThat(orders.totalPriceBeforeDiscount() - BenefitDetails.of(orders, visitDate).totalBenefitAmount())
                .isEqualTo(totalPriceBeforeDiscount - discountAmount);
    }

    @DisplayName("주말 할인 + 크리스마스 디데이 할인이 적용된 금액 계산")
    @ParameterizedTest
    @CsvSource(value = {"1 " + order, "2 " + order, "8 " + order, "9 " + order, "15 " + order, "16 " + order,
            "22 " + order, "23 " + order}, delimiter = ' ')
    void hitChristmasDDayAndWeekendDiscount(String day, String orderInputs) {
        final Integer discountAmount = MAIN_COUNT * WEEKEND_DISCOUNT.amount() + christmasDDayDiscountAmount(day);
        final VisitDate visitDate = VisitDate.of(PureNumber.wrap(day));
        final Basket orders = Basket.of(MultipleOrderInput.of(orderInputs));
        assertThat(orders.totalPriceBeforeDiscount() - BenefitDetails.of(orders, visitDate).totalBenefitAmount())
                .isEqualTo(totalPriceBeforeDiscount - discountAmount);
    }

    @DisplayName("특별 할인 + 평일 할인 + 크리스마스 디데이 할인")
    @ParameterizedTest
    @CsvSource(value = {"3 " + order, "10 " + order, "17 " + order, "24 " + order, "25 " + order}
            , delimiter = ' ')
    void hitSpecialAndWeekdayAndChristmasDDayDiscount(String day, String orderInputs) {
        final VisitDate visitDate = VisitDate.of(PureNumber.wrap(day));
        final Integer discountAmount = DESSERT_COUNT * WEEKDAY_DISCOUNT.amount()
                + SPECIAL_DISCOUNT
                + christmasDDayDiscountAmount(day);
        final Basket orders = Basket.of(MultipleOrderInput.of(orderInputs));
        assertThat(orders.totalPriceBeforeDiscount() - BenefitDetails.of(orders, visitDate).totalBenefitAmount())
                .isEqualTo(totalPriceBeforeDiscount - discountAmount);
    }

    @DisplayName("특별 할인 + 평일 할인")
    @ParameterizedTest
    @CsvSource(value = {"31 " + order}, delimiter = ' ')
    void hitSpecialAndWeekdayDiscount(String day, String orderInputs) {
        final VisitDate visitDate = VisitDate.of(PureNumber.wrap(day));
        final Integer discountAmount = DESSERT_COUNT * WEEKDAY_DISCOUNT.amount()
                + SPECIAL_DISCOUNT;
        final Basket orders = Basket.of(MultipleOrderInput.of(orderInputs));
        assertThat(orders.totalPriceBeforeDiscount() - BenefitDetails.of(orders, visitDate).totalBenefitAmount())
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
