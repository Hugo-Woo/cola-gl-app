package com.cola.gl.api.service;

import com.alibaba.cola.dto.MultiResponse;
import com.cola.gl.api.dto.BalanceDTO;
import com.cola.gl.api.dto.qry.BalanceQry;

public interface BalanceServiceI {
    MultiResponse<BalanceDTO> list(BalanceQry qry);
}
