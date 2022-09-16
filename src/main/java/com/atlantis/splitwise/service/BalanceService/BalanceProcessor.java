package com.atlantis.splitwise.service.BalanceService;

import com.atlantis.splitwise.model.dto.ReportLedger;
import org.springframework.stereotype.Component;

import java.util.List;

public interface BalanceProcessor {
    public List<ReportLedger> getBalanceForUser(Long userId);
    public Double getOverallBalance(Long userId);
}
