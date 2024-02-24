package com.masroufi.api.shared.helpers;

import com.masroufi.api.shared.types.MyDate;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class MyDateHelper {

    public static MyDate getCurrentWeekStartDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate mondayDate = currentDate.with(DayOfWeek.MONDAY);
        return MyDate.builder()
                .second(0)
                .minute(0)
                .hour(0)
                .day(mondayDate.getDayOfMonth())
                .month(mondayDate.getMonthValue())
                .year(mondayDate.getYear())
                .build();
    }

    public static MyDate getCurrentWeekEndDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate sundayDate = currentDate.with(DayOfWeek.SUNDAY);
        return MyDate.builder()
                .second(0)
                .minute(0)
                .hour(0)
                .day(sundayDate.getDayOfMonth())
                .month(sundayDate.getMonthValue())
                .year(sundayDate.getYear())
                .build();
    }

    public static MyDate getLastWeekStartDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastMondayDate = currentDate.minusWeeks(1).with(DayOfWeek.MONDAY);
        return MyDate.builder()
                .second(0)
                .minute(0)
                .hour(0)
                .day(lastMondayDate.getDayOfMonth())
                .month(lastMondayDate.getMonthValue())
                .year(lastMondayDate.getYear())
                .build();
    }

    public static MyDate getLastWeekEndDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastSundayDate = currentDate.minusWeeks(1).with(DayOfWeek.SUNDAY);
        return MyDate.builder()
                .second(0)
                .minute(0)
                .hour(0)
                .day(lastSundayDate.getDayOfMonth())
                .month(lastSundayDate.getMonthValue())
                .year(lastSundayDate.getYear())
                .build();
    }

    public static MyDate getCurrentMonthStartDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        return MyDate.builder()
                .second(0)
                .minute(0)
                .hour(0)
                .day(firstDayOfMonth.getDayOfMonth())
                .month(firstDayOfMonth.getMonthValue())
                .year(firstDayOfMonth.getYear())
                .build();
    }

    public static MyDate getCurrentMonthEndDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        return MyDate.builder()
                .second(0)
                .minute(0)
                .hour(0)
                .day(lastDayOfMonth.getDayOfMonth())
                .month(lastDayOfMonth.getMonthValue())
                .year(lastDayOfMonth.getYear())
                .build();
    }

    public static MyDate getLastMonthStartDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfLastMonth = currentDate.minusMonths(1).withDayOfMonth(1);
        return MyDate.builder()
                .second(0)
                .minute(0)
                .hour(0)
                .day(firstDayOfLastMonth.getDayOfMonth())
                .month(firstDayOfLastMonth.getMonthValue())
                .year(firstDayOfLastMonth.getYear())
                .build();
    }

    public static MyDate getLastMonthEndDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastDayOfLastMonth = currentDate.minusMonths(1).withDayOfMonth(1).minusDays(1);
        return MyDate.builder()
                .second(0)
                .minute(0)
                .hour(0)
                .day(lastDayOfLastMonth.getDayOfMonth())
                .month(lastDayOfLastMonth.getMonthValue())
                .year(lastDayOfLastMonth.getYear())
                .build();
    }
}
