package com.masroufi.api.service;

import com.masroufi.api.dto.request.ActivateDeactivateUserModel;
import com.masroufi.api.dto.UserDetailsDto;
import com.masroufi.api.dto.request.UserPasswordModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDetailsDto updateMyProfileDetails(UserDetailsDto user);

    UserDetailsDto updateMyPassword(UserPasswordModel passwordDto);

    UserDetailsDto getMyProfileDetails();

    UserDetailsDto createUser(UserDetailsDto user);

    UserDetailsDto updateUserDetails(String uuid, UserDetailsDto user);

    UserDetailsDto updateUserPassword(String uuid, UserPasswordModel passwordDto);

    UserDetailsDto deleteUser(String uuid);

    UserDetailsDto activateDeactivateUser(String uuid, ActivateDeactivateUserModel userModel);

    UserDetailsDto getUserDetails(String uuid);

    List<UserDetailsDto> searchUsers();

    UserDetailsDto getUserByEmail(String email);
}
