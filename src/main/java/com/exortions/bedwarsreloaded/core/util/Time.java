package com.exortions.bedwarsreloaded.core.util;

public class Time {

    private final long time;

    public Time(long time) {
        this.time = time;
    }

    public long toMillis() {
        return time;
    }

    public long toSeconds() {
        return time/1000;
    }

    public long toMinutes() {
        return time/60000;
    }

    public long toHours() {
        return time/3600000;
    }

    public long toDays() {
        return time/86400000;
    }

    public long toYears() {
        return time/31556952000L;
    }

    public long toDecades() {
        return time/315569520000L;
    }

    public long toCenturies() {
        return time/3155695200000L;
    }

    public long toMillennia() {
        return time/31557600000000L;
    }

    public long getTime() {
        return time;
    }

    public static Time difference(long origin, long current) {
        return new Time(current-origin);
    }

}
