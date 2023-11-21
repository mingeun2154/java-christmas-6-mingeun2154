package christmas.IO;

public enum ResponseMessage {

    WELCOME_TO_EVENT_PLANNER("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.\n"),
    PREVIEW_EVENT_BENEFITS("%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n"),
    ORDERED_ITEMS("<주문 메뉴>\n"),
    TOTAL_AMOUNT_BEFORE_DISCOUNT("<할인 전 총주문 금액>\n"),
    GIFT_ITEMS("<증정 메뉴>\n"),
    BENEFIT_DETAILS("<혜택 내역>\n"),
    TOTAL_BENEFIT_AMOUNT("<총혜택 금액>\n"),
    TOTAL_AMOUNT_AFTER_DISCOUNT("<할인 후 예상 결제 금액>\n"),
    EVENT_BADGE("<12월 이벤트 배지>\n");

    @Override
    public String toString() {
        return message;
    }

    private final String message;

    private ResponseMessage(String msg) {
        message = msg;
    }
}
