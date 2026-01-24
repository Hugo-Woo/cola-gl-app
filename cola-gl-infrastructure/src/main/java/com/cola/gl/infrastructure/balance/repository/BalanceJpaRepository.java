package com.cola.gl.infrastructure.balance.repository;

import com.cola.gl.infrastructure.db.BalanceDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BalanceJpaRepository extends JpaRepository<BalanceDO, Long> {

    /**
     * 查询覆盖指定期间的余额记录
     * startPeriod <= period < endPeriod
     */
    Optional<BalanceDO> findByAccountCodeAndStartPeriodLessThanEqualAndEndPeriodGreaterThan(
            String accountCode, String startPeriod, String endPeriod);

    /**
     * 查询指定期间之后的余额记录
     */
    List<BalanceDO> findByAccountCodeAndStartPeriodGreaterThanOrderByStartPeriodAsc(
            String accountCode, String startPeriod);
}
