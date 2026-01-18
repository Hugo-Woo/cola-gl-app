package com.cola.gl.infrastructure.balance.repository;

import com.cola.gl.infrastructure.db.BalanceDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceJpaRepository extends JpaRepository<BalanceDO, Long> {
    Optional<BalanceDO> findByAccountCodeAndPeriod(String accountCode, String period);
}
