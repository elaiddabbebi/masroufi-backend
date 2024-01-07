package com.masroufi.api.controller;

import com.masroufi.api.dto.UserDetailsDto;
import com.masroufi.api.dto.request.UserLoginModel;
import com.masroufi.api.security.SecurityConstants;
import com.masroufi.api.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("UserServiceImp")
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginModel loginRequest) {

        System.out.println(loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        String userName = ((User) authentication.getPrincipal()).getUsername();

        String accessToken = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                .compact();

        UserDetailsDto currentUser = userService.getUserByEmail(userName);

        HashMap<String, String> returnValue = new HashMap<String, String>();
        returnValue.put("access_token", SecurityConstants.TOKEN_PREFIX + accessToken);
        returnValue.put("refresh_token", SecurityConstants.TOKEN_PREFIX + refreshToken);
        returnValue.put("uuid", currentUser.getUuid());

        return ResponseEntity.ok(returnValue);
    }
}
