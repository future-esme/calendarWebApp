package utm.tmps.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import utm.tmps.domain.enumeration.MONTH;
import utm.tmps.service.dto.CalendarDTO;
import utm.tmps.service.dto.CalendarDayDTO;
import utm.tmps.service.dto.CalendarWeekDTO;

import java.util.Calendar;

@Service
public class CalendarService {
    private final Logger log = LoggerFactory.getLogger(CalendarService.class);
    private final UserService userService;

    public CalendarService(UserService userService) {
        this.userService = userService;
    }

    public CalendarDTO getCalendarForCurrentUser(MONTH month, Integer year) {
        var currentUser = userService.getCurrentAuthenticatedUser();
        var firstDayOfWeek = currentUser.getUserSettings().getWeekFirstDay();
        var calendar = new CalendarDTO();
        calendar.setWeekDays(firstDayOfWeek);
        calendar.setMonth(month);
        calendar.setYear(year);
        Calendar calendarLibrary = Calendar.getInstance();
        calendarLibrary.setFirstDayOfWeek(Calendar.MONDAY);
        calendarLibrary.set(year, month.ordinal(), 1);
        int startWeekNumber = calendarLibrary.get(Calendar.WEEK_OF_YEAR);
        calendarLibrary.set(Calendar.DAY_OF_MONTH, 28);
        int endWeekNumber = (month.equals(MONTH.DECEMBER)) ? 53 : calendarLibrary.get(Calendar.WEEK_OF_YEAR);
        for (var i = startWeekNumber; i <= endWeekNumber; i++) {
            var newWeek = new CalendarWeekDTO();
            newWeek.setWeekNumber(i);
            for (var j = 1; j <= 7; j++) {
                calendarLibrary.set(Calendar.WEEK_OF_YEAR, i);
                calendarLibrary.set(Calendar.DAY_OF_WEEK, j);
                var dayOfMonth = calendarLibrary.get(Calendar.DAY_OF_MONTH);
                var newDay = new CalendarDayDTO();
                newDay.setDay(dayOfMonth);
                newDay.setTargetMonth((month.ordinal() == calendarLibrary.get(Calendar.MONTH)));
                newDay.setHasEvents(true);
                newWeek.addDay(newDay);
            }
            calendar.addWeek(newWeek);
        }
        return calendar;
    }
}
