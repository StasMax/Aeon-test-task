package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.dto.UserDto;
import org.example.domain.entity.User;
import org.example.domain.vm.UserVM;
import org.example.exception.AuthenticationException;
import org.example.security.jwt.JwtUtils;
import org.example.service.AuthService;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> authorize(UserVM authenticationRequest) {

        User user = userService.findByLogin(authenticationRequest.getLogin());

        if (user == null) {
            throw new AuthenticationException(String.format("Login %s not exist", authenticationRequest.getLogin()));
        }

        if (user.getLockTime() != null && user.getLockTime().isAfter(LocalDateTime.now())) {
            throw new AuthenticationException(
                    String.format(
                            "You account is blocked. Try for %s seconds",
                            LocalDateTime.now().until(user.getLockTime(),
                                    ChronoUnit.SECONDS))
            );
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getLogin(),
                        authenticationRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();

        log.info(String.format("========== User with name %s success authorized", userDetails.getLogin()));

        return ResponseEntity.ok(new UserDto(userDetails.getUsername(), jwt));
    }

}
