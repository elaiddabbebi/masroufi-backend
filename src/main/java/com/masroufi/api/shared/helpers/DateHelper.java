package com.masroufi.api.shared.helpers;

import com.masroufi.api.enums.Day;
import com.masroufi.api.enums.Month;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    public static int getCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getMonth().ordinal();
    }

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

    public static int getCurrentYear() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear();
    }

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

    /********** Other utils ***********/

    public static List<Month> getMonthsOfYear() {
        return Arrays.asList(
                Month.JANUARY,
                Month.FEBRUARY,
                Month.MARCH,
                Month.APRIL,
                Month.MAY,
                Month.JUNE,
                Month.JULY,
                Month.AUGUST,
                Month.SEPTEMBER,
                Month.OCTOBER,
                Month.NOVEMBER,
                Month.DECEMBER
        );
    }

    public static List<Day> getDaysOfWeek() {
        return Arrays.asList(
                Day.MONDAY,
                Day.TUESDAY,
                Day.WEDNESDAY,
                Day.THURSDAY,
                Day.FRIDAY,
                Day.SATURDAY,
                Day.SUNDAY
        );
    }

    /********** Private utils ***********/

    private static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
