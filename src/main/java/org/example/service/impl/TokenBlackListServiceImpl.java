package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.entity.IncorrectToken;
import org.example.repository.TokenBlackListRepository;
import org.example.security.jwt.JwtUtils;
import org.example.service.TokenBlackListService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class TokenBlackListServiceImpl implements TokenBlackListService {

    private final TokenBlackListRepository repository;
    private final JwtUtils jwtUtils;

    @Override
    @Scheduled(fixedDelayString = "${fixedDelay.milliseconds}")
    @Transactional
    public void clearList() {
        List<IncorrectToken> all = repository.findAll();
        if (all.size() != 0) {
            all.removeIf(token -> !jwtUtils.isTokenExpiration(token.getToken()));
            repository.deleteAll(all);
        }
    }

    @Override
    @Transactional
    public IncorrectToken saveIncorrectToken(String token) {
        IncorrectToken incorrectToken = new IncorrectToken();
        incorrectToken.setToken(token);
        return repository.save(incorrectToken);
    }

    @Override
    @Transactional
    public boolean existsIncorrectToken(String token) {
        return repository.existsByToken(token);
    }
}
