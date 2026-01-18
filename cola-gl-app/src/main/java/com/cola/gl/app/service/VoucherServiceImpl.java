package com.cola.gl.app.service;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.cola.gl.api.dto.cmd.VoucherAddCmd;
import com.cola.gl.api.dto.cmd.VoucherPostCmd;
import com.cola.gl.api.dto.cmd.VoucherSubmitCmd;
import com.cola.gl.api.service.VoucherServiceI;
import com.cola.gl.app.executor.VoucherAddCmdExe;
import com.cola.gl.app.executor.VoucherPostCmdExe;
import com.cola.gl.app.executor.VoucherSubmitCmdExe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoucherServiceImpl implements VoucherServiceI {

    private final VoucherAddCmdExe voucherAddCmdExe;
    private final VoucherPostCmdExe voucherPostCmdExe;
    private final VoucherSubmitCmdExe voucherSubmitCmdExe;

    @Override
    public SingleResponse<Long> addVoucher(VoucherAddCmd cmd) {
        return voucherAddCmdExe.execute(cmd);
    }

    @Override
    public Response postVoucher(VoucherPostCmd cmd) {
        return voucherPostCmdExe.execute(cmd);
    }

    @Override
    public Response submitVoucher(VoucherSubmitCmd cmd) {
        return voucherSubmitCmdExe.execute(cmd);
    }
}
