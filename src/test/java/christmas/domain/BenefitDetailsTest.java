package christmas.domain;

import static christmas.domain.event.DiscountEvent.CHRISTMAS_D_DAY_DISCOUNT;
import static christmas.domain.event.DiscountEvent.SPECIAL_DISCOUNT;
import static christmas.domain.event.DiscountEvent.WEEKDAY_DISCOUNT;
import static christmas.domain.event.DiscountEvent.WEEKEND_DISCOUNT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import christmas.IO.MultipleOrderInput;
import christmas.IO.PureNumber;
import christmas.domain.event.BenefitDetails;
import christmas.domain.order.Basket;
import christmas.domain.order.Order;
import christmas.domain.order.VisitDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BenefitDetailsTest {

    static VisitDate visitDate = VisitDate.of(PureNumber.of("3"));
    static Basket orders = Basket.of(MultipleOrderInput.of("티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1"));

    @DisplayName("혜택 내용 기록")
    @Test
    void benefitDetailsTest() {
        BenefitDetails details = BenefitDetails.of(Order.of(orders, visitDate));
        assertThat(details.discountAmountBy(CHRISTMAS_D_DAY_DISCOUNT)).isEqualTo(-1_200);
        assertThat(details.discountAmountBy(WEEKDAY_DISCOUNT)).isEqualTo(-4_046);
        assertThat(details.discountAmountBy(WEEKEND_DISCOUNT)).isEqualTo(0);
        assertThat(details.discountAmountBy(SPECIAL_DISCOUNT)).isEqualTo(-1_000);
        assertThat(details.giftAmount()).isEqualTo(25_000);
        assertThat(details.totalBenefitAmount()).isEqualTo(-31_246);
    }
}
