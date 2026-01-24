package com.cola.gl.domain.balance.gateway;

import com.cola.gl.domain.balance.model.Balance;
import java.util.List;

public interface BalanceGateway {

    /**
     * 查询覆盖指定期间的余额记录
     * 
     * @param accountCode 科目代码
     * @param period      期间
     * @return 覆盖该期间的余额记录
     */
    Balance getByAccountCodeCoveringPeriod(String accountCode, String period);

    /**
     * 查询指定期间之后的所有余额记录
     * 
     * @param accountCode 科目代码
     * @param period      期间
     * @return 该期间之后的余额记录列表
     */
    List<Balance> getByAccountCodeAfterPeriod(String accountCode, String period);

    /**
     * 保存余额记录
     * 
     * @param balance 余额
     */
    void save(Balance balance);

    /**
     * 批量保存余额记录
     * 
     * @param balances 余额列表
     */
    void saveAll(List<Balance> balances);
}
