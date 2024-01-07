package com.masroufi.api.dto;

import com.masroufi.api.dto.response.MenuItemDto;
import com.masroufi.api.entity.Account;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UserDetailsDto {
    private String uuid;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    private String fullName;
    @NotNull
    @NotEmpty
    private String email;
    private Date birthDate;
    private String phoneNumber;
    @NotNull
    private RoleDto role;
    private long age;
    private Boolean isActivated;
    private List<MenuItemDto> items;

    public static UserDetailsDto buildFromUser(Account user) {
        long age = 0;
        if (user.getBirthDate() != null) {
            LocalDate now = LocalDate.now();
            LocalDate birthDate = user.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            age = birthDate.until(now).getYears();
        }
        return UserDetailsDto.builder()
                .uuid(user.getUuid())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .phoneNumber(user.getPhoneNumber())
                .role(RoleDto.buildFromRole(user.getRole()))
                .age(age)
                .isActivated(user.getIsActivated())
                .build();
    }
}
