package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.entity.User;
import org.example.domain.mapper.UserMapper;
import org.example.domain.vm.UserVM;
import org.example.exception.ServiceException;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    @Transactional
    public String addUser(UserVM vm) {

        if (userRepository.existsByLogin(vm.getLogin())) {
            throw new ServiceException(
                    String.format("User with login %s not found", vm.getLogin()), HttpStatus.NOT_FOUND);
        }

        userRepository.save(mapper.toEntity(vm));
        log.info(String.format("========== User with login %s created", vm.getLogin()));
        return String.format("Пользователь с именем %s создан", vm.getLogin());
    }

    @Override
    @Transactional
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public User updateBalance(long userId, BigDecimal balance) {
        User user = userRepository.getOne(userId);
        user.setBalance(balance);
        return userRepository.save(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User updateBrutForceFields(
            long userId, int failedLoginAttempts, LocalDateTime lockTime) {
        User user = userRepository.getOne(userId);
        user.setLockTime(lockTime);
        user.setFailedLoginAttempts(failedLoginAttempts);
        return userRepository.saveAndFlush(user);
    }

}
