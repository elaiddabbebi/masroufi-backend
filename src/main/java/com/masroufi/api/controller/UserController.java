package com.masroufi.api.controller;

import com.masroufi.api.dto.ActivateDeactivateUserModel;
import com.masroufi.api.dto.UserDetailsDto;
import com.masroufi.api.dto.UserPasswordDto;
import com.masroufi.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Qualifier("UserServiceImp")
    private UserService userService;

    @GetMapping("/search")
    List<UserDetailsDto> searchUsers() {
        return this.userService.searchUsers();
    }

    @GetMapping("/{uuid}")
    UserDetailsDto getUserDetails(@PathVariable String uuid) {
        return this.userService.getUserDetails(uuid);
    }

    @PutMapping("/details/{uuid}")
    UserDetailsDto updateUserDetails(@PathVariable String uuid, @RequestBody @Valid UserDetailsDto userDto) {
        return this.userService.updateUserDetails(uuid, userDto);
    }

    @PutMapping("/password/{uuid}")
    UserDetailsDto updateUserPassword(@PathVariable String uuid, @RequestBody @Valid UserPasswordDto passwordDto) {
        return this.userService.updateUserPassword(uuid, passwordDto);
    }

    @PostMapping
    UserDetailsDto createUser(@RequestBody @Valid UserDetailsDto userDto) {
        return this.userService.createUser(userDto);
    }

    @DeleteMapping("/{uuid}")
    UserDetailsDto deleteUser(@PathVariable String uuid) {
        return this.userService.deleteUser(uuid);
    }

    @PatchMapping("/activate-deactivate/{uuid}")
    UserDetailsDto activateDeactivateUser(@PathVariable String uuid, @RequestBody ActivateDeactivateUserModel activateDeactivateUserModel) {
        return this.userService.activateDeactivateUser(uuid, activateDeactivateUserModel);
    }
}
