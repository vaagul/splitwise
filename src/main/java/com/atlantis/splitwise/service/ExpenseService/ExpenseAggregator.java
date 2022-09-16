package com.atlantis.splitwise.service.ExpenseService;

import com.atlantis.splitwise.model.dto.UserExpenseAllotment;
import com.atlantis.splitwise.model.entity.*;
import com.atlantis.splitwise.model.request.ExpenseData;
import com.atlantis.splitwise.utils.enums.TransactionType;
import com.atlantis.splitwise.utils.exceptions.InvalidTransactionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ExpenseAggregator extends BasicExpenseAggregator{

    private void validatePaidAndOwedSplits(ExpenseData expenseData){
        if(!splitValidatorFactory.getSplitValidator(expenseData.getPaidBySplitType()).validate(expenseData.getPaidAmounts(), expenseData.getTotalAmount()) ||
                !splitValidatorFactory.getSplitValidator(expenseData.getOwedBySplitType()).validate(expenseData.getOwedAmounts(), expenseData.getTotalAmount())) {
            throw new InvalidTransactionException("Invalid split format");
        }
    }

    private void addExpenseSplit(List<UserExpenseAllotment> allAllotedExpenses, Expense expense){
        for(UserExpenseAllotment userExpenseAllotment: allAllotedExpenses){
            ExpenseSplit expenseSplit = ExpenseSplit.builder().expense(expense).amount(userExpenseAllotment.getAmount()).userId(userExpenseAllotment.getUserId()).build();
            expenseSplitRepository.save(expenseSplit);
        }
    }

    private void addLedgerInfo(List<UserExpenseAllotment> allAllotedExpenses) {
        int start = 0;
        int end = allAllotedExpenses.size() - 1;
        while(start<end){
            Double difference = allAllotedExpenses.get(end).getAmount() + allAllotedExpenses.get(start).getAmount();
            List<Ledger> allLedgers = ledgerRepository.findByPaidUserIdAndOwedUserId(allAllotedExpenses.get(end).getUserId(), allAllotedExpenses.get(start).getUserId());
            if(allLedgers != null && allLedgers.size() > 0) {
                Ledger ledger = allLedgers.get(0);
                if(Objects.equals(ledger.getPaidUserId(), allAllotedExpenses.get(end).getUserId())){
                    ledger.setAmount(ledger.getAmount() + Math.min(Math.abs(allAllotedExpenses.get(end).getAmount()), Math.abs(allAllotedExpenses.get(start).getAmount())));
                }
                if(Objects.equals(ledger.getPaidUserId(), allAllotedExpenses.get(start).getUserId())){
                    ledger.setAmount(ledger.getAmount() - Math.min(Math.abs(allAllotedExpenses.get(end).getAmount()), Math.abs(allAllotedExpenses.get(start).getAmount())));
                }
                ledgerRepository.save(ledger);
            }
            else {
                Ledger ledger = Ledger.builder().paidUserId(allAllotedExpenses.get(end).getUserId())
                        .owedUserId(allAllotedExpenses.get(start).getUserId())
                        .amount(Math.min(Math.abs(allAllotedExpenses.get(end).getAmount()), Math.abs(allAllotedExpenses.get(start).getAmount())))
                        .build();
                ledgerRepository.save(ledger);
            }
            if(difference == 0){
                allAllotedExpenses.get(start).setAmount((double) 0);
                allAllotedExpenses.get(end).setAmount((double) 0);
                start++;
                end--;
            }
            if(difference > 0){
                allAllotedExpenses.get(start).setAmount((double) 0);
                allAllotedExpenses.get(end).setAmount(difference);
                start++;
            }
            if(difference < 0){
                allAllotedExpenses.get(end).setAmount((double) 0);
                allAllotedExpenses.get(start).setAmount(difference);
                end--;
            }
            log.info(String.valueOf(allAllotedExpenses));
        }
    }


    @Override
    public ExpenseInfo processExpense(Long user, ExpenseData expenseData) {
        if(!isValidUser(user))
            throw new InvalidTransactionException("Invalid user " + user + " given in the header");
        Expense expense = Expense.builder()
                .addedBy(userRepository.findById(user).get().getName())
                .label(expenseData.getLabel())
                .description(expenseData.getDescription())
                .build();
        expenseRepository.save(expense);

        ExpenseInfo expenseInfo = ExpenseInfo.builder()
                .paidBySplitType(expenseData.getPaidBySplitType())
                .paidByInfo(expenseData.getPaidByUserInfo())
                .paidAmounts(expenseData.getPaidAmounts())
                .owedBySplitType(expenseData.getOwedBySplitType())
                .owedByInfo(expenseData.getOwedByUserInfo())
                .owedAmounts(expenseData.getOwedAmounts())
                .totalAmount(expenseData.getTotalAmount())
                .expense(expense)
                .build();
        expenseInfoRepository.save(expenseInfo);

        validatePaidAndOwedSplits(expenseData);

        Map<String, UserExpenseAllotment> payerAllotment = splitValidatorFactory.getSplitValidator(expenseData.getPaidBySplitType())
                .getContribution(TransactionType.PAYER, expenseData.getPaidAmounts(), expenseData.getPaidByUserInfo(), expenseData.getTotalAmount());
        Map<String, UserExpenseAllotment> owerAllotment = splitValidatorFactory.getSplitValidator(expenseData.getOwedBySplitType())
                .getContribution(TransactionType.OWER, expenseData.getOwedAmounts(), expenseData.getOwedByUserInfo(), expenseData.getTotalAmount());
        for(String _user: owerAllotment.keySet()){
            if(payerAllotment.containsKey(_user)){
                owerAllotment.get(_user).setAmount(owerAllotment.get(_user).getAmount() + payerAllotment.get(_user).getAmount());
            }
        }
        List<UserExpenseAllotment> allAllotedExpenses = owerAllotment.values().stream()
                .sorted(Comparator.comparing(UserExpenseAllotment::getAmount)).toList();

        addExpenseSplit(allAllotedExpenses, expense);

        addLedgerInfo(allAllotedExpenses);

        return expenseInfo;

    }

    @Override
    public List<ExpenseInfo> expenseSplitWithUser(Long userId) {
        List<ExpenseSplit> expenseSplitsPerUser = expenseSplitRepository.findByUserId(userId);
        List<Expense> expenseList = expenseSplitsPerUser.stream().map(ExpenseSplit::getExpense).toList();
        return expenseInfoRepository.findByExpenseIn(expenseList);
    }

}
