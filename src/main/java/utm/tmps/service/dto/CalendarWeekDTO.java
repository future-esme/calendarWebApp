package utm.tmps.service.dto;

import java.util.ArrayList;
import java.util.List;

public class CalendarWeekDTO {
    private Integer weekNumber;
    private List<CalendarDayDTO> days;

    public CalendarWeekDTO() {
        this.days = new ArrayList<>();
    }

    public void addDay(CalendarDayDTO dayDTO) {
        this.days.add(dayDTO);
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public List<CalendarDayDTO> getDays() {
        return days;
    }

    public void setDays(List<CalendarDayDTO> days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "CalendarWeekDTO{" +
            "weekNumber=" + weekNumber +
            ", days=" + days +
            '}';
    }
}
