package christmas.IO;

import static christmas.IO.ResponseMessage.BENEFIT_DETAILS;
import static christmas.IO.ResponseMessage.EVENT_BADGE;
import static christmas.IO.ResponseMessage.GIFT_ITEMS;
import static christmas.IO.ResponseMessage.ORDERED_ITEMS;
import static christmas.IO.ResponseMessage.TOTAL_AMOUNT_AFTER_DISCOUNT;
import static christmas.IO.ResponseMessage.TOTAL_AMOUNT_BEFORE_DISCOUNT;
import static christmas.IO.ResponseMessage.TOTAL_BENEFIT_AMOUNT;

import christmas.domain.event.Badge;
import christmas.domain.event.Gift;
import christmas.domain.order.Basket;
import christmas.service.BenefitsPreview;

public class OutputView {

    public OutputView() {
    }

    public void printBenefitPreview(BenefitsPreview preview) {
        final Basket gifts = Gift.of(preview.benefits().totalBenefitAmount());
        printOrderedMenuItems(preview.order().itemsView());
        printTotalAmountBeforeDiscount(preview.order().totalPriceBeforeDiscount());
        printGifts(gifts.itemsView());
        printBenefitDetails(preview.benefits().detailsView());
        printTotalBenefitAmount(preview.benefits().totalBenefitAmount());
        printTotalAmountAfterDiscount(preview.order().totalPriceBeforeDiscount()
                - preview.benefits().totalBenefitAmount()
                - gifts.totalPriceBeforeDiscount());
        printBadge(Badge.of(preview.benefits().totalBenefitAmount()).getName());
    }

    private void printOrderedMenuItems(String items) {
        System.out.print(ORDERED_ITEMS);
        System.out.println(items);
    }

    private void printTotalAmountBeforeDiscount(int amount) {
        System.out.print(TOTAL_AMOUNT_BEFORE_DISCOUNT);
        System.out.println(String.format("%,d원", amount));
    }

    private void printGifts(String gifts) {
        System.out.print(GIFT_ITEMS);
        System.out.println(gifts);
    }

    private void printBenefitDetails(String benefitDetails) {
        System.out.print(BENEFIT_DETAILS);
        System.out.println(benefitDetails);
    }

    private void printTotalBenefitAmount(int amount) {
        System.out.print(TOTAL_BENEFIT_AMOUNT);
        if (amount > 0) {
            System.out.print(String.format("-%,d원\n", amount));
            return;
        }
        System.out.println("0원");
    }

    private void printTotalAmountAfterDiscount(int amount) {
        System.out.print(TOTAL_AMOUNT_AFTER_DISCOUNT);
        System.out.println(String.format("%,d원\n", amount));
    }

    private void printBadge(String badgeName) {
        System.out.print(EVENT_BADGE);
        System.out.println(badgeName);
    }
}
