package com.cola.gl.infrastructure.balance.repository;

import com.cola.gl.infrastructure.db.ContactBalanceDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactBalanceJpaRepository extends JpaRepository<ContactBalanceDO, Long> {

    /**
     * 查询覆盖指定期间的往来余额记录
     * startPeriod <= period < endPeriod
     */
    Optional<ContactBalanceDO> findByAccountCodeAndContactCodeAndStartPeriodLessThanEqualAndEndPeriodGreaterThan(
            String accountCode, String contactCode, String startPeriod, String endPeriod);

    /**
     * 查询指定期间之后的往来余额记录
     */
    List<ContactBalanceDO> findByAccountCodeAndContactCodeAndStartPeriodGreaterThanOrderByStartPeriodAsc(
            String accountCode, String contactCode, String startPeriod);
}
