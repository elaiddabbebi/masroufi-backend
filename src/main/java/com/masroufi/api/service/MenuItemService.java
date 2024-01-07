package com.masroufi.api.service;

import com.masroufi.api.dto.response.MenuItemDto;
import com.masroufi.api.entity.Role;

import java.util.List;

public interface MenuItemService {
    List<MenuItemDto> getRoleMenuItems(Role role);
}
