package com.cola.gl.infrastructure.balance.mapper;

import com.cola.gl.domain.balance.model.Balance;
import com.cola.gl.infrastructure.db.BalanceDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class BalanceMapper {

    public BalanceDO toDO(Balance balance) {
        BalanceDO balanceDO = new BalanceDO();
        BeanUtils.copyProperties(balance, balanceDO);
        return balanceDO;
    }

    public Balance toDomain(BalanceDO balanceDO) {
        if (balanceDO == null)
            return null;
        Balance balance = new Balance();
        BeanUtils.copyProperties(balanceDO, balance);
        return balance;
    }
}
