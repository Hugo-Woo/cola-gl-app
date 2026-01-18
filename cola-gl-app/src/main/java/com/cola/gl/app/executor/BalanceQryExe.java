package com.cola.gl.app.executor;

import com.alibaba.cola.dto.SingleResponse;
import com.cola.gl.api.dto.qry.BalanceQry;
import com.cola.gl.domain.balance.gateway.BalanceGateway;
import com.cola.gl.domain.balance.gateway.ContactBalanceGateway;
import com.cola.gl.domain.balance.model.Balance;
import com.cola.gl.domain.balance.model.ContactBalance;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class BalanceQryExe {

    private final BalanceGateway balanceGateway;
    private final ContactBalanceGateway contactBalanceGateway;

    public SingleResponse<BigDecimal> execute(BalanceQry qry) {
        if (qry.getContactCode() != null && !qry.getContactCode().isEmpty()) {
            ContactBalance contactBalance = contactBalanceGateway.getByAccountAndContact(
                    qry.getAccountCode(), qry.getContactCode(), qry.getPeriod());
            if (contactBalance == null) {
                return SingleResponse.of(BigDecimal.ZERO);
            }
            return SingleResponse.of(contactBalance.getNetBalance());
        }

        Balance balance = balanceGateway.getByAccountAndPeriod(qry.getAccountCode(), qry.getPeriod());
        if (balance == null) {
            return SingleResponse.of(BigDecimal.ZERO);
        }
        return SingleResponse.of(balance.getNetBalance());
    }
}
