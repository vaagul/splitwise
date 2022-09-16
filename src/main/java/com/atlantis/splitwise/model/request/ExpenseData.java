package com.atlantis.splitwise.model.request;

import com.atlantis.splitwise.model.entity.Expense;
import com.atlantis.splitwise.model.entity.ExpenseInfo;
import com.atlantis.splitwise.utils.enums.SplitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ExpenseData {
    String addedBy;

    String label;

    String description;

    SplitType paidBySplitType;

    String paidByUserInfo;

    String paidAmounts;

    SplitType owedBySplitType;

    String owedByUserInfo;

    String owedAmounts;

    Double totalAmount;
}
