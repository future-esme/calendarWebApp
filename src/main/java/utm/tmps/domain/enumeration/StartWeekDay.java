package utm.tmps.domain.enumeration;

public enum StartWeekDay {
    SATURDAY(7),
    SUNDAY(1),
    MONDAY(2);
    private final int calendarOrder;

    StartWeekDay(int calendarOrder) {
        this.calendarOrder = calendarOrder;
    }

    public int getCalendarOrder() {
        return calendarOrder;
    }

    public static int[] getDayOrderByFirstDayOfWeek(StartWeekDay startWeekDay) {
        return switch (startWeekDay) {
            case SATURDAY -> new int[]{7, 1, 2, 3, 4, 5, 6};
            case SUNDAY -> new int[]{1, 2, 3, 4, 5, 6, 7};
            case MONDAY -> new int[]{2, 3, 4, 5, 6, 7, 1};
        };
    }
}
