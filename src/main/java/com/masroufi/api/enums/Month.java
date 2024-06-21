package com.masroufi.api.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Month {
    JANUARY(0),
    FEBRUARY(1),
    MARCH(2),
    APRIL(3),
    MAY(4),
    JUNE(5),
    JULY(6),
    AUGUST(7),
    SEPTEMBER(8),
    OCTOBER(9),
    NOVEMBER(10),
    DECEMBER(11);

    Month(int value) {}

    public static Month of(int value) {
        List<Month> months = Arrays.stream(Month.values()).filter(m -> m.ordinal() == value).toList();
        if (months != null && !months.isEmpty()) {
            return months.get(0);
        } else {
            return null;
        }
    }
}
