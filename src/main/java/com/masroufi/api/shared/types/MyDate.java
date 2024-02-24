package com.masroufi.api.shared.types;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyDate {
    int second;
    int minute;
    int hour;
    int day;
    int month;
    int year;
}
