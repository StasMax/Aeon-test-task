package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Urls;
import org.example.domain.vm.UserVM;
import org.example.exception.ValidationException;
import org.example.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "User Controller")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Создание нового пользователя",
            method = "POST",
            description = "Позволяет создать нового пользователя с дефолтным балансом"
    )
    @PostMapping(value = Urls.REGISTRATION, produces = MediaType.TEXT_PLAIN_VALUE)
    public String createUser(@Valid @RequestBody UserVM vm,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldErrors());
        }
        return userService.addUser(vm);
    }
}
