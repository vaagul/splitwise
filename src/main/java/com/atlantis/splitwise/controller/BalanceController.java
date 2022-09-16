package com.atlantis.splitwise.controller;

import com.atlantis.splitwise.model.dto.ReportLedger;
import com.atlantis.splitwise.resource.BalanceResource;
import com.atlantis.splitwise.service.BalanceService.BalanceAggregator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class BalanceController implements BalanceResource {

    @Autowired
    BalanceAggregator balanceAggregator;

    @Override
    public List<ReportLedger> getBalanceForUser(Long user) {
        return balanceAggregator.getBalanceForUser(user);
    }

    @Override
    public ResponseEntity<Double> getOverallBalance(Long user) {
        return new ResponseEntity<>(balanceAggregator.getOverallBalance(user), HttpStatus.OK);
    }
}
