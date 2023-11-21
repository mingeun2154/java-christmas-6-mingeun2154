package christmas.IO;

public enum Prompt {

    WAIT_FOR_VISIT_DATE("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해주세요!)\n"),
    WAIT_FOR_ORDER("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)\n");

    @Override
    public String toString() {
        return promptMessage;
    }

    private final String promptMessage;

    private Prompt(String msg) {
        promptMessage = msg;
    }
}
