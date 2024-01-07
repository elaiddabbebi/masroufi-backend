package com.masroufi.api.service.impl;

import com.masroufi.api.dto.request.ActivateDeactivateUserModel;
import com.masroufi.api.dto.UserDetailsDto;
import com.masroufi.api.dto.request.UserPasswordModel;
import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.Role;
import com.masroufi.api.repository.AccountRepository;
import com.masroufi.api.repository.RoleRepository;
import com.masroufi.api.service.MenuItemService;
import com.masroufi.api.service.UserService;
import com.masroufi.api.shared.context.ApplicationSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("UserServiceImp")
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ApplicationSecurityContext applicationSecurityContext;

    @Autowired
    private MenuItemService menuItemService;

    @Override
    public UserDetailsDto updateMyProfileDetails(UserDetailsDto user) {
        Account account = this.applicationSecurityContext.getCurrentUser();
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        account.setFirstName(user.getFirstName());
        account.setLastName(user.getLastName());
        account.setBirthDate(user.getBirthDate());
        account.setPhoneNumber(user.getPhoneNumber());
        account = this.accountRepository.save(account);
        return UserDetailsDto.buildFromUser(account);
    }

    @Override
    public UserDetailsDto updateMyPassword(UserPasswordModel passwordDto) {
        if (passwordDto.getOldPassword() == null) {
            throw new RuntimeException("Old password is required!");
        }
        if (!passwordDto.getNewPassword().equals(passwordDto.getPasswordConfirmation())) {
            throw new RuntimeException("Password confirmation error!");
        }
        Account account = this.applicationSecurityContext.getCurrentUser();
        if (account == null) {
            throw new RuntimeException("User not found!");
        }
        String storedPassword = account.getPassword();
        String providedPassword = passwordDto.getOldPassword();
        if(!bCryptPasswordEncoder.matches(providedPassword, storedPassword)) {
            throw new RuntimeException("Provided password is wrong!");
        }
        account.setPassword(bCryptPasswordEncoder.encode(passwordDto.getNewPassword()));
        account = this.accountRepository.save(account);
        return UserDetailsDto.buildFromUser(account);
    }

    @Override
    public UserDetailsDto getMyProfileDetails() {
        Account account = this.applicationSecurityContext.getCurrentUser();
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        UserDetailsDto returnValue = UserDetailsDto.buildFromUser(account);
        returnValue.setItems(this.menuItemService.getRoleMenuItems(account.getRole()));
        return returnValue;
    }

    @Override
    public UserDetailsDto createUser(UserDetailsDto user) {
        Account account = this.accountRepository.findByEmailIgnoreCase(user.getEmail());
        if (account != null) {
            throw new RuntimeException("Email already used!");
        }
        Role userRole = this.roleRepository.findByUuid(user.getRole().getUuid());
        Account newUser = new Account();
        newUser.setRole(userRole);
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setBirthDate(user.getBirthDate());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setIsDeleted(false);
        newUser.setIsActivated(false);
        newUser = this.accountRepository.save(newUser);
        return UserDetailsDto.buildFromUser(newUser);
    }

    @Override
    public UserDetailsDto updateUserDetails(String uuid, UserDetailsDto user) {
        Account account = this.accountRepository.findByUuid(uuid);
        if (account == null) {
            throw new RuntimeException("User not found!");
        }
        Role userRole = this.roleRepository.findByUuid(user.getRole().getUuid());
        account.setRole(userRole);
        account.setEmail(user.getEmail());
        account.setFirstName(user.getFirstName());
        account.setLastName(user.getLastName());
        account.setBirthDate(user.getBirthDate());
        account.setPhoneNumber(user.getPhoneNumber());
        account = this.accountRepository.save(account);
        return UserDetailsDto.buildFromUser(account);
    }

    @Override
    public UserDetailsDto updateUserPassword(String uuid, UserPasswordModel passwordDto) {
        if (!passwordDto.getNewPassword().equals(passwordDto.getPasswordConfirmation())) {
            throw new RuntimeException("Password confirmation error!");
        }
        Account account = this.accountRepository.findByUuid(uuid);
        if (account == null) {
            throw new RuntimeException("User not found!");
        }
        account.setPassword(bCryptPasswordEncoder.encode(passwordDto.getNewPassword()));
        account = this.accountRepository.save(account);
        return UserDetailsDto.buildFromUser(account);
    }

    @Override
    public UserDetailsDto deleteUser(String uuid) {
        Account userToDelete = this.accountRepository.findByUuid(uuid);
        if (userToDelete != null) {
             this.accountRepository.delete(userToDelete);
             return UserDetailsDto.buildFromUser(userToDelete);
        }
        return null;
    }

    @Override
    public UserDetailsDto activateDeactivateUser(String uuid, ActivateDeactivateUserModel userModel) {
        Account userToUpdate = this.accountRepository.findByUuid(uuid);
        if (userToUpdate != null) {
            userToUpdate.setIsActivated(userModel.getIsActivated());
            this.accountRepository.save(userToUpdate);
            return UserDetailsDto.buildFromUser(userToUpdate);
        }
        return null;
    }

    @Override
    public UserDetailsDto getUserDetails(String uuid) {
        Account account = this.accountRepository.findByUuid(uuid);
        if (account != null) {
            return UserDetailsDto.buildFromUser(account);
        }
        return null;
    }

    @Override
    public List<UserDetailsDto> searchUsers() {
        List<Account> accounts = this.accountRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<UserDetailsDto> returnValue = new ArrayList<>();
        for (Account account: accounts) {
            returnValue.add(UserDetailsDto.buildFromUser(account));
        }
        return returnValue;
    }

    @Override
    public UserDetailsDto getUserByEmail(String email) {
        Account account = this.accountRepository.findByEmailIgnoreCase(email);
        if (account != null) {
            return UserDetailsDto.buildFromUser(account);
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.accountRepository.findByEmailIgnoreCase(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(account.getEmail(), account.getPassword(), new ArrayList<GrantedAuthority>());
    }
}
