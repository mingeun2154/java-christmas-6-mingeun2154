package christmas;

import christmas.IO.InputView;
import christmas.IO.OutputView;
import christmas.service.ChristmasEventService;

public class ChristmasPromotionMission implements Mission {

    private final InputView inputView;
    private final OutputView outputView;
    private ChristmasEventService service;

    public ChristmasPromotionMission() {
        inputView = new InputView();
        outputView = new OutputView();
    }

    @Override
    public void run() {
        service = new ChristmasEventService(inputView.readExpectedVisitDate() , inputView.readOrder());
        outputView.printBenefitPreview(service.benefits());
    }
}
