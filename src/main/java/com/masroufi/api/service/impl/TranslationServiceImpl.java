package com.masroufi.api.service.impl;

import com.masroufi.api.enums.AppLocale;
import com.masroufi.api.service.TranslationService;
import com.masroufi.api.shared.context.ApplicationSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TranslationServiceImpl implements TranslationService {

    @Autowired
    private MessageSource frenchMessageSource;

    @Autowired
    private MessageSource englishMessageSource;

    @Autowired
    private ApplicationSecurityContext applicationSecurityContext;

    @Override
    public String translate(String code) {
        AppLocale locale = this.applicationSecurityContext.getUserLocaleOrDefault();
        return this.translate(code, locale);
    }

    @Override
    public String translate(String code, Object[] args) {
        AppLocale locale = this.applicationSecurityContext.getUserLocaleOrDefault();
        return this.translate(code, args, locale);
    }

    @Override
    public String translate(String code, AppLocale locale) {
        try {
            if (AppLocale.EN.equals(locale)) {
                return this.englishMessageSource.getMessage(code, null, Locale.ENGLISH);
            } else {
                return this.frenchMessageSource.getMessage(code, null, Locale.FRENCH
                );
            }
        } catch (Exception ignored) {
            return code;
        }
    }

    @Override
    public String translate(String code, Object[] args, AppLocale locale) {
        try {
            if (AppLocale.EN.equals(locale)) {
                return this.englishMessageSource.getMessage(code, args, Locale.ENGLISH);
            } else {
                return this.frenchMessageSource.getMessage(code, args, Locale.FRENCH);
            }
        } catch (Exception ignored) {
            return code;
        }
    }
}
