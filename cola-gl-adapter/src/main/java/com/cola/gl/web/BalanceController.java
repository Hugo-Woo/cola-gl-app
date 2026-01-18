package com.cola.gl.web;

import com.alibaba.cola.dto.SingleResponse;
import com.cola.gl.api.dto.qry.BalanceQry;
import com.cola.gl.api.service.BalanceServiceI;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/balance")
@AllArgsConstructor
public class BalanceController {

    private final BalanceServiceI balanceService;

    @GetMapping
    public SingleResponse<BigDecimal> getBalance(BalanceQry qry) {
        return balanceService.getBalance(qry);
    }
}
