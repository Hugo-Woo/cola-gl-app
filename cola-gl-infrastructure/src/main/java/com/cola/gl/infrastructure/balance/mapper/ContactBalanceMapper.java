package com.cola.gl.infrastructure.balance.mapper;

import com.cola.gl.domain.balance.model.ContactBalance;
import com.cola.gl.infrastructure.db.ContactBalanceDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ContactBalanceMapper {

    public ContactBalanceDO toDO(ContactBalance balance) {
        ContactBalanceDO balanceDO = new ContactBalanceDO();
        BeanUtils.copyProperties(balance, balanceDO);
        return balanceDO;
    }

    public ContactBalance toDomain(ContactBalanceDO balanceDO) {
        if (balanceDO == null)
            return null;
        ContactBalance balance = new ContactBalance();
        BeanUtils.copyProperties(balanceDO, balance);
        return balance;
    }
}
