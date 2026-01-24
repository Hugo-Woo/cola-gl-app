package com.cola.gl.infrastructure.balance.repository;

import com.cola.gl.domain.balance.gateway.BalanceGateway;
import com.cola.gl.domain.balance.model.Balance;
import com.cola.gl.infrastructure.db.BalanceDO;
import com.cola.gl.infrastructure.balance.mapper.BalanceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class BalanceRepositoryImpl implements BalanceGateway {

    private final BalanceJpaRepository balanceJpaRepository;
    private final BalanceMapper balanceMapper;

    @Override
    public Balance getByAccountCodeCoveringPeriod(String accountCode, String period) {
        Optional<BalanceDO> balanceDO = balanceJpaRepository
                .findByAccountCodeAndStartPeriodLessThanEqualAndEndPeriodGreaterThan(accountCode, period, period);
        return balanceDO.map(balanceMapper::toDomain).orElse(null);
    }

    @Override
    public List<Balance> getByAccountCodeAfterPeriod(String accountCode, String period) {
        List<BalanceDO> balanceDOs = balanceJpaRepository
                .findByAccountCodeAndStartPeriodGreaterThanOrderByStartPeriodAsc(accountCode, period);
        return balanceDOs.stream()
                .map(balanceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Balance balance) {
        BalanceDO balanceDO = balanceMapper.toDO(balance);

        // 如果有ID则更新，否则查询是否存在覆盖的记录
        if (balance.getId() != null) {
            balanceDO.setId(balance.getId());
        }

        balanceJpaRepository.save(balanceDO);
    }

    @Override
    public void saveAll(List<Balance> balances) {
        List<BalanceDO> balanceDOs = balances.stream()
                .map(balance -> {
                    BalanceDO balanceDO = balanceMapper.toDO(balance);
                    if (balance.getId() != null) {
                        balanceDO.setId(balance.getId());
                    }
                    return balanceDO;
                })
                .collect(Collectors.toList());
        balanceJpaRepository.saveAll(balanceDOs);
    }
}
