package christmas.domain;

import static christmas.domain.event.DiscountEvent.CHRISTMAS_D_DAY_DISCOUNT;
import static christmas.domain.event.DiscountEvent.SPECIAL_DISCOUNT;
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
import christmas.domain.order.Order;
import christmas.domain.order.VisitDate;
import christmas.service.ChristmasEventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class DiscountTest {

    static final Integer APPETIZER_COUNT = 1;
    static final Integer MAIN_COUNT = 2;
    static final Integer DESSERT_COUNT = 3;
    static final Integer DRINKS_COUNT = 5;
    static final String order = "양송이수프-1,크리스마스파스타-2,초코케이크-3,제로콜라-5";
    static final Basket basket = Basket.of(MultipleOrderInput.of(order));
    static final Integer totalPriceBeforeDiscount = APPETIZER_COUNT * MUSHROOM_SOUP.getPrice()
            + MAIN_COUNT * CHRISTMAS_PASTA.getPrice()
            + DESSERT_COUNT * CHOCOLATE_CAKE.getPrice()
            + DRINKS_COUNT * ZERO_COKE.getPrice();

    @DisplayName("평일 할인 혜택 금액 계산")
    @ParameterizedTest
    @ValueSource(strings = {"26", "27 ", "28"})
    void weekdayDiscountBenefitAmoun(String day) {
        final Order order = Order.of(basket, VisitDate.of(PureNumber.of(day)));
        int actual = WEEKDAY_DISCOUNT.benefitAmount(order);
        assertThat(actual).isEqualTo(2_023 * DESSERT_COUNT);
    }

    @DisplayName("평일 할인만 적용된 금액 계산")
    @ParameterizedTest
    @ValueSource(strings = {"26", "27 ", "28"})
    void hitOnlyWeekDayDiscount(String day) {
        final Order order = Order.of(basket, VisitDate.of(PureNumber.of(day)));
        final Integer discountAmount = WEEKDAY_DISCOUNT.benefitAmount(order);
        final ChristmasEventService service = new ChristmasEventService(VisitDate.of(PureNumber.of(day)), basket);
        assertThat(service.expectedAmountAfterDiscount())
                .isEqualTo(totalPriceBeforeDiscount - discountAmount);
    }

    @DisplayName("평일 할인 + 크리스마스 디데이 할인이 적용된 금액 계산")
    @ParameterizedTest
    @ValueSource(strings = {"4", "11", "14", "18", "21"})
    void hitChristmasDDayAndWeekDayDiscount(String day) {
        final Order order = Order.of(basket, VisitDate.of(PureNumber.of(day)));
        final Integer discountAmount = WEEKDAY_DISCOUNT.benefitAmount(order)
                + CHRISTMAS_D_DAY_DISCOUNT.benefitAmount(order);
        final ChristmasEventService service = new ChristmasEventService(VisitDate.of(PureNumber.of(day)), basket);
        assertThat(service.expectedAmountAfterDiscount())
                .isEqualTo(totalPriceBeforeDiscount - discountAmount);
    }

    @DisplayName("주말 할인만 적용된 금액 계산")
    @ParameterizedTest
    @ValueSource(strings = {"29", "30"})
    void hitOnlyWeekendDiscount(String day) {
        final Order order = Order.of(basket, VisitDate.of(PureNumber.of(day)));
        final Integer discountAmount = WEEKEND_DISCOUNT.benefitAmount(order);
        final ChristmasEventService service = new ChristmasEventService(VisitDate.of(PureNumber.of(day)), basket);
        assertThat(service.expectedAmountAfterDiscount())
                .isEqualTo(totalPriceBeforeDiscount - discountAmount);
    }

    @DisplayName("주말 할인 + 크리스마스 디데이 할인이 적용된 금액 계산")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "8", "9", "15", "16", "22", "23"})
    void hitChristmasDDayAndWeekendDiscount(String day) {
        final Order order = Order.of(basket, VisitDate.of(PureNumber.of(day)));
        final Integer discountAmount = WEEKEND_DISCOUNT.benefitAmount(order) +
                CHRISTMAS_D_DAY_DISCOUNT.benefitAmount(order);
        final ChristmasEventService service = new ChristmasEventService(VisitDate.of(PureNumber.of(day)), basket);
        assertThat(service.expectedAmountAfterDiscount())
                .isEqualTo(totalPriceBeforeDiscount - discountAmount);
    }

    @DisplayName("특별 할인 + 평일 할인 + 크리스마스 디데이 할인")
    @ParameterizedTest
    @ValueSource(strings = {"3", "10", "17", "24", "25"})
    void hitSpecialAndWeekdayAndChristmasDDayDiscount(String day) {
        final Order order = Order.of(basket, VisitDate.of(PureNumber.of(day)));
        final VisitDate visitDate = VisitDate.of(PureNumber.of(day));
        final Integer discountAmount =WEEKDAY_DISCOUNT.benefitAmount(order)
                + SPECIAL_DISCOUNT.benefitAmount(order)
                + CHRISTMAS_D_DAY_DISCOUNT.benefitAmount(order);
        final ChristmasEventService service = new ChristmasEventService(VisitDate.of(PureNumber.of(day)), basket);
        assertThat(service.expectedAmountAfterDiscount())
                .isEqualTo(totalPriceBeforeDiscount - discountAmount);
    }

    @DisplayName("특별 할인 + 평일 할인")
    @ParameterizedTest
    @ValueSource(strings = {"31"})
    void hitSpecialAndWeekdayDiscount(String day) {
        final Order order = Order.of(basket, VisitDate.of(PureNumber.of(day)));
        final Integer discountAmount = WEEKDAY_DISCOUNT.benefitAmount(order)
                + SPECIAL_DISCOUNT.benefitAmount(order);
        final ChristmasEventService service = new ChristmasEventService(VisitDate.of(PureNumber.of(day)), basket);
        assertThat(service.expectedAmountAfterDiscount())
                .isEqualTo(totalPriceBeforeDiscount - discountAmount);
    }

}
