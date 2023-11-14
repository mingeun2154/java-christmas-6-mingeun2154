package christmas;

import christmas.IO.InputView;
import christmas.IO.OutputView;
import christmas.domain.event.Badge;
import christmas.domain.event.BenefitDetails;
import christmas.domain.event.Gift;
import christmas.domain.order.Basket;
import christmas.domain.order.VisitDate;

public class ChristmasPromotionMission implements Mission {

    private final InputView inputView;
    private final OutputView outputView;
    private VisitDate visitDate;
    private Basket orders;
    private Basket gifts;
    private BenefitDetails benefitDetails;

    public ChristmasPromotionMission() {
        inputView = new InputView();
        outputView = new OutputView();
    }

    @Override
    public void run() {
        visitDate = inputView.readExpectedVisitDate();
        orders = inputView.readOrder();
        gifts = Gift.of(orders.totalPriceBeforeDiscount());
        benefitDetails = BenefitDetails.of(orders, visitDate);
        outputView.printOrderedMenuItems(orders.itemsView());
        outputView.printTotalAmountBeforeDiscount(orders.totalPriceBeforeDiscount());
        outputView.printGifts(gifts.itemsView());
        outputView.printBenefitDetails(benefitDetails.detailsView());
        outputView.printTotalBenefitAmount(benefitDetails.totalBenefitAmount());
        outputView.printTotalAmountAfterDiscount(orders.totalPriceBeforeDiscount()
                - benefitDetails.totalBenefitAmount()
                + gifts.totalPriceBeforeDiscount());
        outputView.printBadge(Badge.of(benefitDetails.totalBenefitAmount()).getName());
    }
}
