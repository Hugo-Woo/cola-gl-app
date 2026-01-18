package com.cola.gl.api.service;

import com.alibaba.cola.dto.SingleResponse;
import com.cola.gl.api.dto.qry.BalanceQry;
import java.math.BigDecimal;

public interface BalanceServiceI {
    SingleResponse<BigDecimal> getBalance(BalanceQry qry);
}
