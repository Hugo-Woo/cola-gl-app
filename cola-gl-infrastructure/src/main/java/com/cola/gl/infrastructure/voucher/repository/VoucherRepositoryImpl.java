package com.cola.gl.infrastructure.voucher.repository;

import com.cola.gl.domain.voucher.gateway.VoucherGateway;
import com.cola.gl.domain.voucher.model.Voucher;
import com.cola.gl.infrastructure.db.VoucherDO;
import com.cola.gl.infrastructure.voucher.mapper.VoucherMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class VoucherRepositoryImpl implements VoucherGateway {

    private final VoucherJpaRepository voucherJpaRepository;
    private final VoucherMapper voucherMapper;

    @Override
    public void save(Voucher voucher) {
        VoucherDO voucherDO = voucherMapper.toDO(voucher);
        // If it's an update, we might need to handle lines carefully, but for now
        // typical save
        // JPA handles ID setting on save
        VoucherDO saved = voucherJpaRepository.save(voucherDO);
        voucher.setId(saved.getId());
    }

    @Override
    public Voucher getById(Long id) {
        Optional<VoucherDO> voucherDO = voucherJpaRepository.findById(id);
        return voucherDO.map(voucherMapper::toDomain).orElse(null);
    }
}
