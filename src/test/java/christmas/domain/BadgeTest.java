package christmas.domain;

import christmas.domain.event.Badge;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BadgeTest {

    @DisplayName("총혜택 금액에 맞는 배지 부여")
    @ParameterizedTest
    @CsvSource(value = {"2023,없음", "5000,별", "10000,트리", "20000,산타"})
    void badgeTest(String amount, String badgeName) {
        int benefitAmount = Integer.parseInt(amount);
        Assertions.assertThat(Badge.of(benefitAmount).getName()).isEqualTo(badgeName);
    }
}
