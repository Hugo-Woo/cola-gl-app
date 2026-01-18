package com.cola.gl.api.dto.cmd;

import com.alibaba.cola.dto.Command;
import lombok.Data;

@Data
public class VoucherPostCmd extends Command {

    private Long voucherId;
}
