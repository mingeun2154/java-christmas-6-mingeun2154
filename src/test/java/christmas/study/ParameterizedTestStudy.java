package christmas.study;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ParameterizedTestStudy {

    @DisplayName("CsvSource 사용법")
    @ParameterizedTest
    @CsvSource({"hello, world", "woowa, course"})
    void howToUseCsvSource(String str1, String str2) {
        assertThat(str1).isIn("hello", "woowa");
        assertThat(str2).isIn("world", "course");
    }

    @DisplayName("ValueSource 사용법")
    @ParameterizedTest
    @ValueSource(strings = {"hello, world", "woowa, course"})
    void howToUseValueSource(String str) {
        assertThat(str).isIn("hello, world", "woowa, course");
    }
}
