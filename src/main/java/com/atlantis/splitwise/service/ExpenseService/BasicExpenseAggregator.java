package com.atlantis.splitwise.service.ExpenseService;

import com.atlantis.splitwise.db.*;
import com.atlantis.splitwise.model.entity.User;
import com.atlantis.splitwise.service.SplitProcessor.SplitValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public abstract class BasicExpenseAggregator implements ExpenseProcessor{
    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    ExpenseInfoRepository expenseInfoRepository;

    @Autowired
    LedgerRepository ledgerRepository;

    @Autowired
    SplitValidatorFactory splitValidatorFactory;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExpenseSplitRepository expenseSplitRepository;

    boolean isValidUser(Long user) {
        Optional<User> userObject = userRepository.findById(user);
        return userObject.isPresent();
    }

}
