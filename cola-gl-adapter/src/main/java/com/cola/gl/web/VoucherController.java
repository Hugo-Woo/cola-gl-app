package com.cola.gl.web;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.cola.gl.api.dto.cmd.VoucherAddCmd;
import com.cola.gl.api.dto.cmd.VoucherPostCmd;
import com.cola.gl.api.dto.cmd.VoucherSubmitCmd;
import com.cola.gl.api.service.VoucherServiceI;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voucher")
@AllArgsConstructor
public class VoucherController {

    private final VoucherServiceI voucherService;

    @PostMapping
    public SingleResponse<Long> addVoucher(@RequestBody VoucherAddCmd cmd) {
        return voucherService.addVoucher(cmd);
    }

    @PostMapping("/post")
    public Response postVoucher(@RequestBody VoucherPostCmd cmd) {
        return voucherService.postVoucher(cmd);
    }

    @PostMapping("/submit")
    public Response submitVoucher(@RequestBody VoucherSubmitCmd cmd) {
        return voucherService.submitVoucher(cmd);
    }
}
