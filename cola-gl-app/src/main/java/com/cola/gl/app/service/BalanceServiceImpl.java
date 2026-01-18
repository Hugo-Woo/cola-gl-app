package com.cola.gl.app.service;

import com.alibaba.cola.dto.SingleResponse;
import com.cola.gl.api.dto.qry.BalanceQry;
import com.cola.gl.api.service.BalanceServiceI;
import com.cola.gl.app.executor.BalanceQryExe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class BalanceServiceImpl implements BalanceServiceI {

    private final BalanceQryExe balanceQryExe;

    @Override
    public SingleResponse<BigDecimal> getBalance(BalanceQry qry) {
        return balanceQryExe.execute(qry);
    }
}
