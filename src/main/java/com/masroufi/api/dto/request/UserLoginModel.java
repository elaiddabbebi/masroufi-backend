package com.masroufi.api.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UserLoginModel {
    @Email
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @Length(min=3, max=25)
    private String password;
}
