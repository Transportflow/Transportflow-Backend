package online.transportflow.backend.utils;

import java.util.Date;

public class TimeUtils {
    public static String getRelativeTime(Date to) {
        return getRelativeTime(to, System.currentTimeMillis());
    }

    public static String getRelativeTime(Date to, long relative) {
        int relativeWhen = Long.valueOf((to.getTime() - relative) / 60000).intValue();
        var prefix = relativeWhen > 0 ? "+" : "";
        if (relativeWhen < 59) {
            return prefix + relativeWhen + "'";
        } else
            return prefix + relativeWhen / 60 + "h";
    }

    public static String getClockTime(Date from) {
        var hours = String.valueOf(from.getHours());
        var minutes = String.valueOf(from.getMinutes());
        return ("0" + hours).substring(hours.length()-1) + ":" + ("0" + minutes).substring(minutes.length()-1);
    }
}
