package com.cola.gl.api.dto.qry;

import com.alibaba.cola.dto.Query;
import lombok.Data;

@Data
@lombok.EqualsAndHashCode(callSuper = false)
public class BalanceQry extends Query {
    private String accountCode;
    private String period;
    private String contactCode;
}
