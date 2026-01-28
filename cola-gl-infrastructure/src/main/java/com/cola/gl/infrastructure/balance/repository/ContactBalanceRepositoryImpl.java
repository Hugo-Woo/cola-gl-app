package com.cola.gl.infrastructure.balance.repository;

import com.cola.gl.domain.balance.gateway.ContactBalanceGateway;
import com.cola.gl.domain.balance.model.ContactBalance;
import com.cola.gl.infrastructure.balance.mapper.ContactBalanceMapper;
import com.cola.gl.infrastructure.db.ContactBalanceDO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ContactBalanceRepositoryImpl implements ContactBalanceGateway {

    private final ContactBalanceJpaRepository contactBalanceJpaRepository;
    private final ContactBalanceMapper contactBalanceMapper;

    @Override
    public ContactBalance getByAccountAndContactCoveringPeriod(String accountCode, String contactCode, String period) {
        Optional<ContactBalanceDO> balanceDO = contactBalanceJpaRepository
                .findByAccountCodeAndContactCodeAndStartPeriodLessThanEqualAndEndPeriodGreaterThan(
                        accountCode, contactCode, period, period);
        return balanceDO.map(contactBalanceMapper::toDomain).orElse(null);
    }

    @Override
    public List<ContactBalance> getByAccountAndContactAfterPeriod(String accountCode, String contactCode,
            String period) {
        List<ContactBalanceDO> balanceDOs = contactBalanceJpaRepository
                .findByAccountCodeAndContactCodeAndStartPeriodGreaterThanOrderByStartPeriodAsc(
                        accountCode, contactCode, period);
        return balanceDOs.stream()
                .map(contactBalanceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(ContactBalance contactBalance) {
        ContactBalanceDO balanceDO = contactBalanceMapper.toDO(contactBalance);

        // 如果有ID则更新，否则查询是否存在覆盖的记录
        if (contactBalance.getId() != null) {
            balanceDO.setId(contactBalance.getId());
        }

        contactBalanceJpaRepository.save(balanceDO);
    }

    @Override
    public void saveAll(List<ContactBalance> contactBalances) {
        List<ContactBalanceDO> balanceDOs = contactBalances.stream()
                .map(balance -> {
                    ContactBalanceDO balanceDO = contactBalanceMapper.toDO(balance);
                    if (balance.getId() != null) {
                        balanceDO.setId(balance.getId());
                    }
                    return balanceDO;
                })
                .collect(Collectors.toList());
        contactBalanceJpaRepository.saveAll(balanceDOs);
    }
}
