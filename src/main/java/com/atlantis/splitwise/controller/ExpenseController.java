package com.atlantis.splitwise.controller;

import com.atlantis.splitwise.db.ExpenseInfoRepository;
import com.atlantis.splitwise.db.ExpenseRepository;
import com.atlantis.splitwise.db.UserRepository;
import com.atlantis.splitwise.model.entity.ExpenseInfo;
import com.atlantis.splitwise.model.request.ExpenseData;
import com.atlantis.splitwise.resource.ExpenseResource;
import com.atlantis.splitwise.service.ExpenseService.ExpenseAggregator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ExpenseController implements ExpenseResource {

    @Autowired
    ExpenseAggregator expenseAggregator;

    @Override
    public List<ExpenseInfo> getExpensesSplitWithUser(Long user) {
        return expenseAggregator.expenseSplitWithUser(user);
    }


    @Override
    public ExpenseInfo addExpense(Long user, ExpenseData expenseData) {
        log.info(String.valueOf(expenseData));
        return expenseAggregator.processExpense(user, expenseData);
    }
}
