package com.atlantis.splitwise.service.SplitProcessor;

import com.atlantis.splitwise.model.dto.UserExpenseAllotment;
import com.atlantis.splitwise.utils.enums.SplitType;
import com.atlantis.splitwise.utils.enums.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface SplitProcessor {

    public SplitType getSplitType();

    public boolean validate(String splitInfo, Double totalAmount);

    public Map<String, UserExpenseAllotment> getContribution(TransactionType transactionType, String splitInfo, String splitUserInfo, Double totalAmount);

}
