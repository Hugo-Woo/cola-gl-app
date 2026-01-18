package com.cola.gl.domain.balance.gateway;

import com.cola.gl.domain.balance.model.Balance;
import java.util.Optional;

public interface BalanceGateway {
    Balance getByAccountAndPeriod(String accountCode, String period);
    void save(Balance balance);
}
