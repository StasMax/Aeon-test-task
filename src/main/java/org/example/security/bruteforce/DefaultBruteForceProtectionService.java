package org.example.security.bruteforce;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.entity.User;
import org.example.exception.AuthenticationException;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultBruteForceProtectionService implements BruteForceProtectionService {

    @Value("${brute.force.failedlogin.count}")
    private int maxFailedLogins;

    private final UserService userService;

    @Value("${brute.force.lock.minute}")
    private int lockMinute;


    @Override
    public void registerLoginFailure(String username) {
        User user = userService.findByLogin(username);

        try {
            if (user.getLockTime() != null && user.getLockTime().isBefore(LocalDateTime.now())) {
                user.setLockTime(null);
                user.setFailedLoginAttempts(0);
            }

            int failedCounter = user.getFailedLoginAttempts();

            if (maxFailedLogins < failedCounter + 1) {
                user.setLockTime(LocalDateTime.now().plusMinutes(lockMinute));
                log.info(String.format("Account with login %s is blocked", username));
                throw new AuthenticationException(
                        String.format("Incorrect password. Account with login %s is blocked", username));
            } else {
                user.setFailedLoginAttempts(failedCounter + 1);
                throw new AuthenticationException(
                        String.format(
                                "Incorrect password. %s attempts left",
                                maxFailedLogins - user.getFailedLoginAttempts())
                );
            }
        } finally {
            userService.updateBrutForceFields(
                    user.getId(), user.getFailedLoginAttempts(), user.getLockTime());
        }
    }

    @Override
    public void resetBruteForceCounter(String username) {
        User user = userService.findByLogin(username);
        if (user != null) {
            user.setFailedLoginAttempts(0);
            user.setLockTime(null);
            userService.updateBrutForceFields(
                    user.getId(), user.getFailedLoginAttempts(), user.getLockTime());
        }
    }

}