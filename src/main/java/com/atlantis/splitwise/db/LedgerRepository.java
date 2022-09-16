package com.atlantis.splitwise.db;

import com.atlantis.splitwise.model.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long> {

    @Query(value = "select l from Ledger l where (l.paidUserId=:paidUserId AND l.owedUserId=:owedUserId) OR ((l.paidUserId=:owedUserId AND l.owedUserId=:paidUserId))")
    List<Ledger> findByPaidUserIdAndOwedUserId(@Param("paidUserId")Long paidUserId, @Param("owedUserId")Long owedUserId);

    @Query(value = "select l from Ledger l where l.paidUserId=:userId OR l.owedUserId=:userId")
    List<Ledger> findByUserId(@Param("userId")Long userId);

}