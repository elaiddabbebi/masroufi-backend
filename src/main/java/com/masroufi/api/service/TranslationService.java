package com.masroufi.api.service;

import com.masroufi.api.enums.AppLocale;

public interface TranslationService {
    String translate(String code);

    String translate(String code, Object[] args);

    String translate(String code, AppLocale locale);

    String translate(String code, Object[] args, AppLocale locale);
}
