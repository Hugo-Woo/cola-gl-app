package com.cola.gl.infrastructure.balance.repository;

import com.cola.gl.domain.balance.gateway.ContactBalanceGateway;
import com.cola.gl.domain.balance.model.ContactBalance;
import com.cola.gl.infrastructure.balance.mapper.ContactBalanceMapper;
import com.cola.gl.infrastructure.db.ContactBalanceDO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ContactBalanceRepositoryImpl implements ContactBalanceGateway {

    private final ContactBalanceJpaRepository contactBalanceJpaRepository;
    private final ContactBalanceMapper contactBalanceMapper;

    @Override
    public ContactBalance getByAccountAndContact(String accountCode, String contactCode, String period) {
        Optional<ContactBalanceDO> balanceDO = contactBalanceJpaRepository
                .findByAccountCodeAndContactCodeAndPeriod(accountCode, contactCode, period);
        return balanceDO.map(contactBalanceMapper::toDomain).orElse(null);
    }

    @Override
    public void save(ContactBalance contactBalance) {
        ContactBalanceDO balanceDO = contactBalanceMapper.toDO(contactBalance);

        Optional<ContactBalanceDO> existing = contactBalanceJpaRepository.findByAccountCodeAndContactCodeAndPeriod(
                contactBalance.getAccountCode(), contactBalance.getContactCode(), contactBalance.getPeriod());

        existing.ifPresent(e -> balanceDO.setId(e.getId()));

        contactBalanceJpaRepository.save(balanceDO);
    }
}
