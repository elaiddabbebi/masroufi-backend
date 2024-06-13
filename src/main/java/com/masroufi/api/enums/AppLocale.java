package com.masroufi.api.enums;

import java.util.Arrays;
import java.util.List;

public enum AppLocale {
    FR,
    EN;

    public static AppLocale defaultLocale() {
        return FR;
    }

    public static List<AppLocale> all() {
        return Arrays.asList(FR, EN);
    }
}
