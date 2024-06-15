package com.masroufi.api.dto.request;

import com.masroufi.api.enums.AppLocale;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLanguageConfigModel {
    private AppLocale locale;
}
