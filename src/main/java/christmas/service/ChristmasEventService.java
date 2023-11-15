package christmas.service;

import christmas.domain.event.BenefitDetails;
import christmas.domain.order.Basket;
import christmas.domain.order.VisitDate;

public class ChristmasEventService {

    private final VisitDate visitDate;
    private final Basket order;
    private final BenefitDetails benefits;

    public ChristmasEventService(VisitDate visitDate, Basket order) {
        this.order = order;
        this.visitDate = visitDate;
        this.benefits = BenefitDetails.of(order, visitDate);
    }

    public BenefitsPreview benefits() {
        return new BenefitsPreview(order, benefits);
    }

}
