package com.atlantis.splitwise.service.BalanceService;

import com.atlantis.splitwise.db.ExpenseInfoRepository;
import com.atlantis.splitwise.db.LedgerRepository;
import com.atlantis.splitwise.db.UserRepository;
import com.atlantis.splitwise.model.dto.ReportLedger;
import com.atlantis.splitwise.model.entity.Ledger;
import com.atlantis.splitwise.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BalanceAggregator implements BalanceProcessor {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LedgerRepository ledgerRepository;

    @Override
    public List<ReportLedger> getBalanceForUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            return null;
        }
        List<Ledger> ledgers = ledgerRepository.findByUserId(userId);
        List<ReportLedger> reportLedgers = new ArrayList<>();
        for(Ledger ledger: ledgers){
            if(Objects.equals(ledger.getPaidUserId(), userId))
                reportLedgers.add(new ReportLedger(user, userRepository.findById(ledger.getOwedUserId()).orElse(null), ledger.getAmount()));
            else
                reportLedgers.add(new ReportLedger(user, userRepository.findById(ledger.getPaidUserId()).orElse(null), -1 * ledger.getAmount()));
        }
        return reportLedgers;
    }

    @Override
    public Double getOverallBalance(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            return null;
        }
        List<Ledger> ledgers = ledgerRepository.findByUserId(userId);
        List<ReportLedger> reportLedgers = new ArrayList<>();
        Double total = 0D;
        for(Ledger ledger:ledgers){
            if(Objects.equals(ledger.getPaidUserId(), userId))
                total += ledger.getAmount();
            else
                total -= ledger.getAmount();
        }
        return total;
    }
}
