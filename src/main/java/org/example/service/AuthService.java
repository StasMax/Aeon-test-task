package org.example.service;

import org.example.domain.dto.UserDto;
import org.example.domain.vm.UserVM;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    /**
     * Метод авторизации.
     *
     * @param authenticationRequest логин/пароль
     * @return токен
     */
    ResponseEntity<UserDto> authorize(UserVM authenticationRequest);

}
