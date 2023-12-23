package com.masroufi.api.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemDto {
    private String code;
    private String label;
    private String icon;
    private String routerLink;
    private boolean expanded;
    private boolean visible;
    private List<MenuItemDto> items;
}
