package christmas.domain.order;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import christmas.IO.PureNumber;
import christmas.exceptions.InValidVisitDateException;
import java.time.LocalDate;
import java.time.Month;

public class VisitDate {

    private static final Month EVENT_MONTH = Month.DECEMBER;
    private static final int EVENT_YEAR = 2023;
    private static final Integer FIRST_DAY = 1;
    private static final Integer LAST_DAY = 31;
    private final LocalDate visitDate;

    private VisitDate(Month month, int day) {
        visitDate = LocalDate.of(EVENT_YEAR, month, day);
    }

    public static VisitDate of(PureNumber day) throws InValidVisitDateException {
        validateVisitDate(day.getNumber());
        return new VisitDate(EVENT_MONTH, day.getNumber());
    }

    public int getDay() {
        return visitDate.getDayOfMonth();
    }

    public boolean isWeekend() {
        return (visitDate.getDayOfWeek() == FRIDAY || visitDate.getDayOfWeek() == SATURDAY);
    }

    public boolean isWeekday() {
        return !isWeekend();
    }

    private static void validateVisitDate(int day) throws InValidVisitDateException {
        if (!isDayInEventMonth(day))
            throw new InValidVisitDateException();
    }

    private static boolean isDayInEventMonth(int day) {
        return (FIRST_DAY <= day && day <= LAST_DAY);
    }
}
