package com.cola.gl.domain.balance.gateway;

import com.cola.gl.domain.balance.model.ContactBalance;
import java.util.List;

public interface ContactBalanceGateway {

    /**
     * 查询覆盖指定期间的往来余额记录
     * 
     * @param accountCode 科目代码
     * @param contactCode 往来单位代码
     * @param period      期间
     * @return 覆盖该期间的往来余额记录
     */
    ContactBalance getByAccountAndContactCoveringPeriod(String accountCode, String contactCode, String period);

    /**
     * 查询指定期间之后的所有往来余额记录
     * 
     * @param accountCode 科目代码
     * @param contactCode 往来单位代码
     * @param period      期间
     * @return 该期间之后的往来余额记录列表
     */
    List<ContactBalance> getByAccountAndContactAfterPeriod(String accountCode, String contactCode, String period);

    /**
     * 保存往来余额记录
     * 
     * @param contactBalance 往来余额
     */
    void save(ContactBalance contactBalance);

    /**
     * 批量保存往来余额记录
     * 
     * @param contactBalances 往来余额列表
     */
    void saveAll(List<ContactBalance> contactBalances);
}
