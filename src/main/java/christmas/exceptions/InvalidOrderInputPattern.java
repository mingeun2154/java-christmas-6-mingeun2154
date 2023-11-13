package christmas.exceptions;

public class InvalidOrderInputPattern extends IllegalArgumentException {
    private static final String ERROR_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    public InvalidOrderInputPattern() {
        super(ERROR_MESSAGE);
    }
}
