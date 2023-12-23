package com.masroufi.api.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
