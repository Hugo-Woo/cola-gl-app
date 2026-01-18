package com.cola.gl.api.service;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.cola.gl.api.dto.cmd.VoucherAddCmd;
import com.cola.gl.api.dto.cmd.VoucherPostCmd;

public interface VoucherServiceI {
    SingleResponse<Long> addVoucher(VoucherAddCmd cmd);

    Response postVoucher(VoucherPostCmd cmd);
}
