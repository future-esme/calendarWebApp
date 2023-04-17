package utm.tmps.service.dto;

import java.util.Objects;

public class CalendarDayDTO {
    private Integer day;
    private boolean targetMonth;
    private boolean hasEvents;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public boolean isTargetMonth() {
        return targetMonth;
    }

    public void setTargetMonth(boolean targetMonth) {
        this.targetMonth = targetMonth;
    }

    public boolean isHasEvents() {
        return hasEvents;
    }

    public void setHasEvents(boolean hasEvents) {
        this.hasEvents = hasEvents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarDayDTO that = (CalendarDayDTO) o;
        return targetMonth == that.targetMonth && hasEvents == that.hasEvents && Objects.equals(day, that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, targetMonth, hasEvents);
    }

    @Override
    public String toString() {
        return "CalendarDayDTO{" +
            "day=" + day +
            ", targetMonth=" + targetMonth +
            ", hasEvents=" + hasEvents +
            '}';
    }
}
