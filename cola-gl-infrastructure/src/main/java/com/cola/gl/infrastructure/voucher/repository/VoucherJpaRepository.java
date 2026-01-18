package com.cola.gl.infrastructure.voucher.repository;

import com.cola.gl.infrastructure.db.VoucherDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherJpaRepository extends JpaRepository<VoucherDO, Long> {
}
