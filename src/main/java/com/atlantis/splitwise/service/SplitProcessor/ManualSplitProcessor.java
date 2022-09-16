package com.atlantis.splitwise.service.SplitProcessor;

import com.atlantis.splitwise.db.UserRepository;
import com.atlantis.splitwise.model.dto.UserExpenseAllotment;
import com.atlantis.splitwise.utils.enums.SplitType;
import com.atlantis.splitwise.utils.enums.TransactionType;
import com.atlantis.splitwise.utils.exceptions.InvalidTransactionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ManualSplitProcessor implements SplitProcessor {

    @Autowired
    UserRepository userRepository;

    @Override
    public SplitType getSplitType() {
        return SplitType.MANUAL;
    }

    @Override
    public boolean validate(String splitInfo, Double totalAmount) {
        String[] paySplits = splitInfo.split("\\s+");
        try {
            double total = 0;
            for(String pay: paySplits){
                total += Double.parseDouble(pay);
                log.info(String.valueOf(total));
            }
            log.info(String.valueOf(totalAmount));
            if (total == totalAmount){
                log.info("in");
                return true;
            }
        }
        catch (Exception ex){
            throw new InvalidTransactionException(ex.getMessage());
        }
        return false;
    }

    @Override
    public Map<String, UserExpenseAllotment> getContribution(TransactionType transactionType, String splitInfo, String splitUserInfo, Double totalAmount) {
        String[] paySplits = splitInfo.split("\\s+");
        String[] userSplits = splitUserInfo.split("\\s+");
        Map<String, UserExpenseAllotment> userExpenseMap = new HashMap<>();
        int variableMultiplier = (transactionType == TransactionType.PAYER) ? 1 : -1;
        if(paySplits.length != userSplits.length)
            throw new InvalidTransactionException("Invalid entry count for split " + splitUserInfo + " : " + splitInfo);
        try {
            for(int index=0; index < userSplits.length; index++){
                UserExpenseAllotment userExpenseAllotment = UserExpenseAllotment.builder()
                        .amount(variableMultiplier * Double.parseDouble(paySplits[index]))
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
