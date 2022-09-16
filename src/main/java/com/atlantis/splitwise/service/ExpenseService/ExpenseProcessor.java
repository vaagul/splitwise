package com.atlantis.splitwise.service.ExpenseService;

import com.atlantis.splitwise.model.entity.ExpenseInfo;
import com.atlantis.splitwise.model.request.ExpenseData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExpenseProcessor {
    public ExpenseInfo processExpense(Long user, ExpenseData expenseData);

    public List<ExpenseInfo> expenseSplitWithUser(Long userId);

}
