package com.cola.gl.infrastructure.balance.repository;

import com.cola.gl.infrastructure.db.ContactBalanceDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactBalanceJpaRepository extends JpaRepository<ContactBalanceDO, Long> {
    Optional<ContactBalanceDO> findByAccountCodeAndContactCodeAndPeriod(String accountCode, String contactCode,
            String period);
}
