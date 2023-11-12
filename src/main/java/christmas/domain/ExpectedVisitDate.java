package christmas.domain;

import christmas.common.PureNumber;
import christmas.exceptions.InValidVisitDateException;
import java.time.Month;
import java.time.MonthDay;

public class ExpectedVisitDate {

    private static final Month EVENT_MONTH = Month.DECEMBER;
    private static final Integer FIRST_DAY = 1;
    private static final Integer LAST_DAY = 31;
    private MonthDay date;

    private ExpectedVisitDate(Month month, int day) {
        date = MonthDay.of(month, day);
    }

    public static  ExpectedVisitDate of(PureNumber day) throws InValidVisitDateException {
        validateVisitDate(day.getNumber());
        return new ExpectedVisitDate(EVENT_MONTH, day.getNumber());
    }

    private static void validateVisitDate(int day) throws InValidVisitDateException {
        if (!isDayInEventMonth(day))
            throw new InValidVisitDateException();
    }

    private static boolean isDayInEventMonth(int day) {
        return (FIRST_DAY <= day && day <= LAST_DAY);
    }
}
