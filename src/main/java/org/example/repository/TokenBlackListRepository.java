package org.example.repository;

import org.example.domain.entity.IncorrectToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlackListRepository extends JpaRepository<IncorrectToken, Long> {

    boolean existsByToken(String token);
}
