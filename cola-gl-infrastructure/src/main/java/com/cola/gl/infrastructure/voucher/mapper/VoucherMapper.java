package com.cola.gl.infrastructure.voucher.mapper;

import com.cola.gl.domain.voucher.model.Voucher;
import com.cola.gl.domain.voucher.model.VoucherLine;
import com.cola.gl.infrastructure.db.VoucherDO;
import com.cola.gl.infrastructure.db.VoucherLineDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoucherMapper {

    public VoucherDO toDO(Voucher voucher) {
        VoucherDO voucherDO = new VoucherDO();
        BeanUtils.copyProperties(voucher, voucherDO);
        if (voucher.getLines() != null) {
            List<VoucherLineDO> lineDOs = voucher.getLines().stream().map(this::toLineDO).collect(Collectors.toList());
            voucherDO.setLines(lineDOs);
        }
        return voucherDO;
    }

    public Voucher toDomain(VoucherDO voucherDO) {
        if (voucherDO == null)
            return null;
        Voucher voucher = new Voucher();
        BeanUtils.copyProperties(voucherDO, voucher);
        if (voucherDO.getLines() != null) {
            List<VoucherLine> lines = voucherDO.getLines().stream().map(this::toLineDomain)
                    .collect(Collectors.toList());
            voucher.setLines(lines);
        }
        return voucher;
    }

    private VoucherLineDO toLineDO(VoucherLine line) {
        VoucherLineDO lineDO = new VoucherLineDO();
        BeanUtils.copyProperties(line, lineDO);
        return lineDO;
    }

    private VoucherLine toLineDomain(VoucherLineDO lineDO) {
        VoucherLine line = new VoucherLine();
        BeanUtils.copyProperties(lineDO, line);
        return line;
    }
}
