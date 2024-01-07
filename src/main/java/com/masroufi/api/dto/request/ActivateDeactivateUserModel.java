package com.masroufi.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActivateDeactivateUserModel {
    private Boolean isActivated;
}
