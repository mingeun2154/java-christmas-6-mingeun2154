package christmas.IO;

import static christmas.IO.ResponseMessage.BENEFIT_DETAILS;
import static christmas.IO.ResponseMessage.EVENT_BADGE;
import static christmas.IO.ResponseMessage.GIFT_ITEMS;
import static christmas.IO.ResponseMessage.ORDERED_ITEMS;
import static christmas.IO.ResponseMessage.TOTAL_AMOUNT_AFTER_DISCOUNT;
import static christmas.IO.ResponseMessage.TOTAL_AMOUNT_BEFORE_DISCOUNT;
import static christmas.IO.ResponseMessage.TOTAL_BENEFIT_AMOUNT;

public class OutputView {

    public OutputView() { }

    public void printOrderedMenuItems(String items) {
        System.out.print(ORDERED_ITEMS);
        System.out.println(items);
    }

    public void printTotalAmountBeforeDiscount(int amount) {
        System.out.print(TOTAL_AMOUNT_BEFORE_DISCOUNT);
        System.out.println(String.format("%,d원", amount));
    }

    public void printGifts(String gifts) {
        System.out.print(GIFT_ITEMS);
        System.out.println(gifts);
    }

    public void printBenefitDetails(String benefitDetails) {
        System.out.print(BENEFIT_DETAILS);
        System.out.println(benefitDetails);
    }

    public void printTotalBenefitAmount(int amount) {
        System.out.print(TOTAL_BENEFIT_AMOUNT);
        if (amount > 0) {
            System.out.print(String.format("-%,d원\n", amount));
            return;
        }
        System.out.println("0원");
    }

    public void printTotalAmountAfterDiscount(int amount) {
        System.out.print(TOTAL_AMOUNT_AFTER_DISCOUNT);
        System.out.println(String.format("%,d원\n", amount));
    }

    public void printBadge(String badgeName) {
        System.out.print(EVENT_BADGE);
        System.out.println(badgeName);
    }
}
