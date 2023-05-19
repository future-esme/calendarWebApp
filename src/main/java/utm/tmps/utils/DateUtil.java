package utm.tmps.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss").withZone(ZoneId.systemDefault());
}
