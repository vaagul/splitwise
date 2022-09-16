package com.atlantis.splitwise.service.SplitProcessor;

import com.atlantis.splitwise.model.dto.UserExpenseAllotment;
import com.atlantis.splitwise.utils.enums.SplitType;
import com.atlantis.splitwise.utils.enums.TransactionType;
import com.atlantis.splitwise.utils.exceptions.InvalidTransactionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class EqualSplitProcessor implements SplitProcessor {

    @Override
    public SplitType getSplitType() {
        return SplitType.EQUAL;
    }

    @Override
    public boolean validate(String splitInfo, Double totalAmount) {
        if(!splitInfo.trim().equals(""))
            log.warn("The Splitdata given for equal split will not be considered");
        return true;
    }

    @Override
    public Map<String, UserExpenseAllotment> getContribution(TransactionType transactionType, String splitInfo, String splitUserInfo, Double totalAmount) {
        String[] userSplits = splitUserInfo.split("\\s+");
        Map<String, UserExpenseAllotment> userExpenseMap = new HashMap<>();
        int variableMultiplier = (transactionType == TransactionType.PAYER) ? 1 : -1;
        try {
            for(int index=0; index < userSplits.length; index++){
                UserExpenseAllotment userExpenseAllotment = UserExpenseAllotment.builder()
                        .amount(variableMultiplier * (totalAmount)/ userSplits.length)
                        .userId(Long.valueOf(userSplits[index]))
                        .build();
                userExpenseMap.put(userSplits[index], userExpenseAllotment);
            }
        }
        catch (Exception ex){
            throw new InvalidTransactionException(ex.getMessage());
        }
        return userExpenseMap;
    }

}
