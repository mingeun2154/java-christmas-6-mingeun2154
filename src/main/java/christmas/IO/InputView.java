package christmas.IO;

import static christmas.IO.Prompt.WAIT_FOR_ORDER;
import static christmas.IO.Prompt.WAIT_FOR_VISIT_DATE;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.order.Basket;
import christmas.domain.order.VisitDate;

public class InputView {

    public VisitDate readExpectedVisitDate() {
        System.out.print(WAIT_FOR_VISIT_DATE);
        return readUntilUserInputValidDate();
    }

    public Basket readOrder() {
        System.out.print(WAIT_FOR_ORDER);
        return readUntilUserInputValidOrder();
    }

    public void closeConsole() {
        Console.close();
    }

    private VisitDate readUntilUserInputValidDate() {
        while (true) {
            try {
                return VisitDate.of(PureNumber.of(Console.readLine()));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Basket readUntilUserInputValidOrder() {
        while (true) {
            try {
                return Basket.of(MultipleOrderInput.of(Console.readLine()));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}