package com.cola.gl.domain.balance.gateway;

import com.cola.gl.domain.balance.model.ContactBalance;

public interface ContactBalanceGateway {
    ContactBalance getByAccountAndContact(String accountCode, String contactCode, String period);

    void save(ContactBalance contactBalance);
}
