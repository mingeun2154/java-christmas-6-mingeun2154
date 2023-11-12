package christmas.common;

public class NonPureNumberException extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "[ERROR] 숫자만 입력 가능합니다.";

    public NonPureNumberException() {
        super(ERROR_MESSAGE);
    }
}
