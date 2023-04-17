package utm.tmps.service.dto;

import utm.tmps.domain.enumeration.MONTH;
import utm.tmps.domain.enumeration.StartWeekDay;
import utm.tmps.domain.enumeration.WeekDays;

import java.util.ArrayList;
import java.util.List;

public class CalendarDTO {
    private List<WeekDays> weekDays;

    private MONTH month;

    private Integer year;

    private List<CalendarWeekDTO> weeks;

    public CalendarDTO() {
        this.weeks = new ArrayList<>();
    }

    public void addWeek(CalendarWeekDTO weekDTO) {
        this.weeks.add(weekDTO);
    }

    public List<WeekDays> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(StartWeekDay startWeekDay) {
        switch (startWeekDay) {
            case MONDAY -> {
                this.weekDays.add(WeekDays.MONDAY);
                this.weekDays.add(WeekDays.TUESDAY);
                this.weekDays.add(WeekDays.WEDNESDAY);
                this.weekDays.add(WeekDays.THURSDAY);
                this.weekDays.add(WeekDays.FRIDAY);
                this.weekDays.add(WeekDays.SATURDAY);
                this.weekDays.add(WeekDays.SUNDAY);
            }
            case SUNDAY -> {
                this.weekDays.add(WeekDays.SUNDAY);
                this.weekDays.add(WeekDays.MONDAY);
                this.weekDays.add(WeekDays.TUESDAY);
                this.weekDays.add(WeekDays.WEDNESDAY);
                this.weekDays.add(WeekDays.THURSDAY);
                this.weekDays.add(WeekDays.FRIDAY);
                this.weekDays.add(WeekDays.SATURDAY);
            }
            case SATURDAY -> {
                this.weekDays.add(WeekDays.SATURDAY);
                this.weekDays.add(WeekDays.SUNDAY);
                this.weekDays.add(WeekDays.MONDAY);
                this.weekDays.add(WeekDays.TUESDAY);
                this.weekDays.add(WeekDays.WEDNESDAY);
                this.weekDays.add(WeekDays.THURSDAY);
                this.weekDays.add(WeekDays.FRIDAY);
            }
        }
    }

    public MONTH getMonth() {
        return month;
    }

    public void setMonth(MONTH month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<CalendarWeekDTO> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<CalendarWeekDTO> weeks) {
        this.weeks = weeks;
    }

    @Override
    public String toString() {
        return "CalendarDTO{" +
            "weekDays=" + weekDays +
            ", month=" + month +
            ", year=" + year +
            ", weeks=" + weeks +
            '}';
    }
}
