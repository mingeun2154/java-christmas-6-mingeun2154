package christmas.domain.order;

import christmas.IO.PureNumber;
import christmas.exceptions.InValidVisitDateException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.function.IntUnaryOperator;

public class VisitDate {

    private static final Month EVENT_MONTH = Month.DECEMBER;
    private static final int EVENT_YEAR = 2023;
    private static final Integer FIRST_DAY = 1;
    private static final Integer LAST_DAY = 31;
    private final LocalDate visitDate;

    public static VisitDate of(PureNumber day) throws InValidVisitDateException {
        validateVisitDate(day.getNumber());
        return new VisitDate(EVENT_MONTH, day.getNumber());
    }

    public boolean matchDaysOfMonth(int startDayOfMonthInclude, int endDayOfMonthInclude) {
        return (visitDate.getDayOfMonth() >= startDayOfMonthInclude
                && visitDate.getDayOfMonth() <= endDayOfMonthInclude);
    }

    public boolean matchDaysOfMonth(List<Integer> daysOfMonth) {
        return daysOfMonth.contains(visitDate.getDayOfMonth());
    }

    public boolean matchDaysOfWeek(List<DayOfWeek> daysOfWeek) {
        return daysOfWeek.contains(visitDate.getDayOfWeek());
    }

    public int applyLinearExpressionOnDayOfMonth(IntUnaryOperator expression) {
        return expression.applyAsInt(visitDate.getDayOfMonth());
    }

    private static void validateVisitDate(int day) throws InValidVisitDateException {
        if (!isDayInMonth(day)) {
            throw new InValidVisitDateException();
        }
    }

    private static boolean isDayInMonth(int day) {
        return (FIRST_DAY <= day && day <= LAST_DAY);
    }

    private VisitDate(Month month, int day) {
        visitDate = LocalDate.of(EVENT_YEAR, month, day);
    }
}
