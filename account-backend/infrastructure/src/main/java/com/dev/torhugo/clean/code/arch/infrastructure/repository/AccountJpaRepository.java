package com.dev.torhugo.clean.code.arch.infrastructure.repository;

import com.dev.torhugo.clean.code.arch.infrastructure.repository.models.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByEmail(final String email);
}
