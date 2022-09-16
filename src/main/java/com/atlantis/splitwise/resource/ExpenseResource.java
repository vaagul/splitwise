package com.atlantis.splitwise.resource;

import com.atlantis.splitwise.model.entity.ExpenseInfo;
import com.atlantis.splitwise.model.request.ExpenseData;
import com.atlantis.splitwise.utils.constants.RequestHeaderConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface ExpenseResource {
    @GetMapping("/expense/all")
    List<ExpenseInfo> getExpensesSplitWithUser(@RequestHeader(RequestHeaderConstants.USER) Long user);

    @PostMapping("/expense")
    ExpenseInfo addExpense(@RequestHeader(RequestHeaderConstants.USER) Long user, @RequestBody ExpenseData expenseData);

}
