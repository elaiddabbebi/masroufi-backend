package com.masroufi.api.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Day {
    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5),
    SUNDAY(6);

    Day(int value) {}

    public static Day of(int value) {
        List<Day> days = Arrays.stream(Day.values()).filter(m -> m.ordinal() == value).collect(Collectors.toList());
        if (days != null && !days.isEmpty()) {
            return days.get(0);
        } else {
            return null;
        }
    }
}
