package com.masroufi.api.shared.helpers;

import com.masroufi.api.shared.types.MyDate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MyDateHelper {

    public static MyDate getCurrentWeekStartDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate mondayDate = currentDate.with(DayOfWeek.MONDAY);
        return MyDate.builder()
                .second(0)
                .minute(0)
                .hour(0)
                .day(mondayDate.getDayOfMonth())
                .month(mondayDate.getMonthValue() - 1)
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
                .month(sundayDate.getMonthValue() - 1)
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
                .month(lastMondayDate.getMonthValue() - 1)
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
                .month(lastSundayDate.getMonthValue() - 1)
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
                .month(firstDayOfMonth.getMonthValue() - 1)
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
                .month(lastDayOfMonth.getMonthValue() - 1)
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
                .month(firstDayOfLastMonth.getMonthValue() - 1)
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
                .month(lastDayOfLastMonth.getMonthValue() - 1)
                .year(lastDayOfLastMonth.getYear())
                .build();
    }

    public static MyDate fromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        } else {
            calendar.setTime(new Date());
        }
        return MyDate.builder()
                .second(calendar.get(Calendar.SECOND))
                .minute(calendar.get(Calendar.MINUTE))
                .hour(calendar.get(Calendar.HOUR))
                .day(calendar.get(Calendar.DAY_OF_MONTH))
                .month(calendar.get(Calendar.MONTH))
                .year(calendar.get(Calendar.YEAR))
                .build();
    }

    public static MyDate now() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return MyDate.builder()
                .second(calendar.get(Calendar.SECOND))
                .minute(calendar.get(Calendar.MINUTE))
                .hour(calendar.get(Calendar.HOUR))
                .day(calendar.get(Calendar.DAY_OF_MONTH))
                .month(calendar.get(Calendar.MONTH))
                .year(calendar.get(Calendar.YEAR))
                .build();
    }

    public static MyDate today() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return MyDate.builder()
                .second(0)
                .minute(0)
                .hour(0)
                .day(calendar.get(Calendar.DAY_OF_MONTH))
                .month(calendar.get(Calendar.MONTH))
                .year(calendar.get(Calendar.YEAR))
                .build();
    }
}
