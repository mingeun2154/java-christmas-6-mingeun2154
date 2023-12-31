package christmas.domain;

import static christmas.domain.event.DiscountEvent.WEEKDAY_DISCOUNT;
import static christmas.domain.event.DiscountEvent.WEEKEND_DISCOUNT;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import christmas.IO.PureNumber;
import christmas.domain.order.VisitDate;
import java.time.DayOfWeek;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Test
    void weekend() {
        assertThat(VisitDate.of(PureNumber.of("1")).matchDaysOfWeek(List.of(FRIDAY, SATURDAY))).isTrue();
        assertThat(VisitDate.of(PureNumber.of("3")).matchDaysOfWeek(List.of(FRIDAY, SATURDAY))).isFalse();
    }
}
