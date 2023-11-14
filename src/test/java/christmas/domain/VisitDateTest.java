package christmas.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import christmas.IO.PureNumber;
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
                () -> VisitDate.of(PureNumber.wrap(input))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("유효한 방문 예상 날짜")
    @ParameterizedTest
    @CsvSource({"1", "16", "31"})
    void validVisitDate(String input) {
        Assertions.assertDoesNotThrow(
                () -> VisitDate.of(PureNumber.wrap(input))
        );
    }

    /**
     * 년도는 VisitDate의 static 필드에서 설정
     */

    @DisplayName("입력된 날짜가 주말(금,토)인지 확인(2023년 기준)")
    @ParameterizedTest
    @CsvSource({"1,4", "2,12", "8,20", "9,28", "15,25", "16,3", "22,10", "23,31", "29,19", "30,18"})
    void isTodayAWeekend(String weekend, String weekday) {
        assertThat(VisitDate.of(PureNumber.wrap(weekend)).isWeekend()).isTrue();
        assertThat(VisitDate.of(PureNumber.wrap(weekday)).isWeekend()).isFalse();
    }

    @DisplayName("입력된 날짜가 평일(일~목)인지 확인(2023년 기준)")
    @ParameterizedTest
    @CsvSource({"1,4", "2,12", "8,20", "9,28", "15,25", "16,3", "22,10", "23,31", "29,19", "30,18"})
    void isTodayAWeekDay(String weekend, String weekday) {
        assertThat(VisitDate.of(PureNumber.wrap(weekend)).isWeekday()).isFalse();
        assertThat(VisitDate.of(PureNumber.wrap(weekday)).isWeekday()).isTrue();
    }
}
