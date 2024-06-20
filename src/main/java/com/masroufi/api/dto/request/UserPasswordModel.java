package com.masroufi.api.dto.request;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UserPasswordModel {
    private String oldPassword;
    @NotNull
    @NotEmpty
    private String newPassword;
    @NotNull
    @NotEmpty
    private String passwordConfirmation;
}
