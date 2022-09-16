package com.atlantis.splitwise.resource;

import com.atlantis.splitwise.model.dto.ReportLedger;
import com.atlantis.splitwise.utils.constants.RequestHeaderConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface BalanceResource {
    @GetMapping("/balance/user")
    List<ReportLedger> getBalanceForUser(@RequestHeader(RequestHeaderConstants.USER) Long user);

    @GetMapping("/balance/all")
    ResponseEntity<Double> getOverallBalance(@RequestHeader(RequestHeaderConstants.USER) Long user);
}
