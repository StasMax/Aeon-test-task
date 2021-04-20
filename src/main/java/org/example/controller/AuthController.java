package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Urls;
import org.example.domain.dto.UserDto;
import org.example.domain.vm.UserVM;
import org.example.exception.ValidationException;
import org.example.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Auth Controller")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Авторизация",
            method = "POST",
            description = "Позволяет авторизироваться"
    )
    @PostMapping(value = Urls.LOGIN, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> authenticate(
            @Valid @RequestBody UserVM authenticationRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldErrors());
        }
        return authService.authorize(authenticationRequest);
    }

}
