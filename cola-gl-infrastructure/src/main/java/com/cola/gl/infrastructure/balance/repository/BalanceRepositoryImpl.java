package com.cola.gl.infrastructure.balance.repository;

import com.cola.gl.domain.balance.gateway.BalanceGateway;
import com.cola.gl.domain.balance.model.Balance;
import com.cola.gl.infrastructure.db.BalanceDO;
import com.cola.gl.infrastructure.balance.mapper.BalanceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class BalanceRepositoryImpl implements BalanceGateway {

    private final BalanceJpaRepository balanceJpaRepository;
    private final BalanceMapper balanceMapper;

    @Override
    public Balance getByAccountAndPeriod(String accountCode, String period) {
        Optional<BalanceDO> balanceDO = balanceJpaRepository.findByAccountCodeAndPeriod(accountCode, period);
        return balanceDO.map(balanceMapper::toDomain).orElse(null);
    }

    @Override
    public void save(Balance balance) {
        BalanceDO balanceDO = balanceMapper.toDO(balance);

        // Check if existing record needs ID for update
        Optional<BalanceDO> existing = balanceJpaRepository.findByAccountCodeAndPeriod(balance.getAccountCode(),
                balance.getPeriod());
        existing.ifPresent(e -> balanceDO.setId(e.getId()));

        balanceJpaRepository.save(balanceDO);
    }
}
