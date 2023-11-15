package christmas.domain;

import static christmas.domain.event.discount.perItem.ChristmasPromotion.WEEKDAY_DISCOUNT;
import static christmas.domain.event.discount.perItem.ChristmasPromotion.WEEKEND_DISCOUNT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import christmas.IO.PureNumber;
import christmas.domain.event.discount.perOrder.SpecialDiscount;
import christmas.domain.order.VisitDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class VisitDateTest {

    @DisplayName("유효하지 않은 방문 예상 날짜 입력 -> 예외 발생")
    @ParameterizedTest
    @CsvSource({"2k", "0", "2 2", "32", "100"})
    void invalidVisitDate(String input) {
        assertThatThrownBy(
                () -> VisitDate.of(PureNumber.of(input))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("유효한 방문 예상 날짜")
    @ParameterizedTest
    @CsvSource({"1", "16", "31"})
    void validVisitDate(String input) {
        Assertions.assertDoesNotThrow(
                () -> VisitDate.of(PureNumber.of(input))
        );
    }

    /**
     * 년도는 VisitDate의 static 필드에서 설정
     */

    @DisplayName("입력된 날짜가 주말(금,토)인지 확인(2023년 기준)")
    @ParameterizedTest
    @CsvSource({"1,4", "2,12", "8,20", "9,28", "15,25", "16,3", "22,10", "23,31", "29,19", "30,18"})
    void isTodayAWeekend(String weekend, String weekday) {
        assertThat(WEEKEND_DISCOUNT.matchPeriod(VisitDate.of(PureNumber.of(weekend)))).isTrue();
        assertThat(WEEKEND_DISCOUNT.matchPeriod(VisitDate.of(PureNumber.of(weekday)))).isFalse();
    }

    @DisplayName("입력된 날짜가 평일(일~목)인지 확인(2023년 기준)")
    @ParameterizedTest
    @CsvSource({"1,4", "2,12", "8,20", "9,28", "15,25", "16,3", "22,10", "23,31", "29,19", "30,18"})
    void isTodayAWeekDay(String weekend, String weekday) {
        assertThat(WEEKDAY_DISCOUNT.matchPeriod(VisitDate.of(PureNumber.of(weekend)))).isFalse();
        assertThat(WEEKDAY_DISCOUNT.matchPeriod(VisitDate.of(PureNumber.of(weekday)))).isTrue();
    }

    /**
     * 별이 표시된 날짜들은 VisitDate의 static 필드에서 설정
     */

    @DisplayName("별이 표시된 날짜인지 아닌지 판정")
    @ParameterizedTest
    @CsvSource({"3,1", "10,13", "17,18", "24,26", "25,29", "31,30"})
    void isTodayStarMarkedDay(String star, String non) {
        assertThat(SpecialDiscount.inEventPeriod(VisitDate.of(PureNumber.of(star)))).isTrue();
        assertThat(SpecialDiscount.inEventPeriod(VisitDate.of(PureNumber.of(non)))).isFalse();
    }
}
