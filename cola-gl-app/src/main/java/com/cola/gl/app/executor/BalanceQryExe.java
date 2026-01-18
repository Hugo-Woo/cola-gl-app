package com.cola.gl.app.executor;

import com.alibaba.cola.dto.SingleResponse;
import com.cola.gl.api.dto.qry.BalanceQry;
import com.cola.gl.domain.balance.gateway.BalanceGateway;
import com.cola.gl.domain.balance.model.Balance;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class BalanceQryExe {

    private final BalanceGateway balanceGateway;

    public SingleResponse<BigDecimal> execute(BalanceQry qry) {
        Balance balance = balanceGateway.getByAccountAndPeriod(qry.getAccountCode(), qry.getPeriod());
        return SingleResponse.of(balance.getNetBalance());
    }
}
