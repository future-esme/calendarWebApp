package utm.tmps.service;

import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import utm.tmps.domain.enumeration.Month;
import utm.tmps.domain.enumeration.StartWeekDay;
import utm.tmps.repository.EventRepository;
import utm.tmps.service.dto.CalendarDTO;
import utm.tmps.service.dto.CalendarDayDTO;
import utm.tmps.service.dto.CalendarWeekDTO;

@Service
public class CalendarFacadeService {

    private final Logger log = LoggerFactory.getLogger(CalendarFacadeService.class);
    private final UserService userService;
    private final EventRepository eventRepository;

    public CalendarFacadeService(UserService userService, EventRepository eventRepository) {
        this.userService = userService;
        this.eventRepository = eventRepository;
    }

    public CalendarDTO getCalendarForCurrentUser(Month month, Integer year) {
        log.debug("Get calendar for current user");
        var currentUser = userService.getCurrentAuthenticatedUser();
        var firstDayOfWeek = currentUser.getUserSettings().getWeekFirstDay();
        var eventDays = eventRepository.getDaysWithEvents(month.ordinal() + 1, year);
        var calendar = new CalendarDTO();
        calendar.setWeekDays(firstDayOfWeek);
        calendar.setMonth(month);
        calendar.setYear(year);
        Calendar calendarLibrary = Calendar.getInstance();
        calendarLibrary.setFirstDayOfWeek(firstDayOfWeek.getCalendarOrder());
        calendarLibrary.set(year, month.ordinal(), 1);
        int startWeekNumber = calendarLibrary.get(Calendar.WEEK_OF_YEAR);
        calendarLibrary.set(Calendar.DAY_OF_MONTH, calendarLibrary.getActualMaximum(Calendar.DATE));
        int endWeekNumber = (month.equals(Month.DECEMBER)) ? 53 : calendarLibrary.get(Calendar.WEEK_OF_YEAR);
        for (var i = startWeekNumber; i <= endWeekNumber; i++) {
            var newWeek = new CalendarWeekDTO();
            if (Boolean.TRUE.equals(currentUser.getUserSettings().getWeekNumber())) newWeek.setWeekNumber(i);
            for (var j : StartWeekDay.getDayOrderByFirstDayOfWeek(firstDayOfWeek)) {
                calendarLibrary.set(Calendar.WEEK_OF_YEAR, i);
                calendarLibrary.set(Calendar.DAY_OF_WEEK, j);
                var dayOfMonth = calendarLibrary.get(Calendar.DAY_OF_MONTH);
                var newDay = new CalendarDayDTO();
                newDay.setDay(dayOfMonth);
                newDay.setTargetMonth((month.ordinal() == calendarLibrary.get(Calendar.MONTH)));
                newDay.setHasEvents((newDay.isTargetMonth() && (eventDays.contains(dayOfMonth))));
                newWeek.addDay(newDay);
            }
            calendar.addWeek(newWeek);
        }
        return calendar;
    }
}
