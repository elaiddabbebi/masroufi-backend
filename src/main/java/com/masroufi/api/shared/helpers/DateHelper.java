package com.masroufi.api.shared.helpers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateHelper {

    /********** Week utils ***********/

    public static Date getCurrentWeekStartDate() {
        LocalDate currentDate = LocalDate.now();
        return toDate(currentDate.with(DayOfWeek.MONDAY));
    }

    public static Date getCurrentWeekEndDate() {
        LocalDate currentDate = LocalDate.now();
        return toDate(currentDate.with(DayOfWeek.SUNDAY));
    }

    public static Date getLastWeekStartDate() {
        LocalDate currentDate = LocalDate.now();
        return toDate(currentDate.minusWeeks(1).with(DayOfWeek.MONDAY));
    }

    public static Date getLastWeekEndDate() {
        LocalDate currentDate = LocalDate.now();
        return toDate(currentDate.minusWeeks(1).with(DayOfWeek.SUNDAY));
    }

    /********** Month utils ***********/

    public static Date getCurrentMonthStartDate() {
        LocalDate currentDate = LocalDate.now();
        return toDate(currentDate.withDayOfMonth(1));
    }

    public static Date getCurrentMonthEndDate() {
        LocalDate currentDate = LocalDate.now();
        return toDate(currentDate.withDayOfMonth(currentDate.lengthOfMonth()));
    }

    public static Date getLastMonthStartDate() {
        LocalDate currentDate = LocalDate.now();
        return toDate(currentDate.minusMonths(1).withDayOfMonth(1));
    }

    public static Date getLastMonthEndDate() {
        LocalDate currentDate = LocalDate.now();
        return toDate(currentDate.withDayOfMonth(1).minusDays(1));
    }

    /********** Year utils ***********/

    public static Date getCurrentYearStartDate() {
        LocalDate currentDate = LocalDate.now();
        return toDate(currentDate.withDayOfYear(1));
    }

    public static Date getCurrentYearEndDate() {
        LocalDate currentDate = LocalDate.now();
        return toDate(currentDate.withDayOfYear(currentDate.lengthOfYear()));
    }

    public static Date getLastYearStartDate() {
        LocalDate currentDate = LocalDate.now();
        return toDate(currentDate.minusYears(1).withDayOfYear(1));
    }

    public static Date getLastYearEndDate() {
        LocalDate currentDate = LocalDate.now();
        return toDate(currentDate.withDayOfYear(1).minusDays(1));
    }

    /********** Generic utils ***********/

    private static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
