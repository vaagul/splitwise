package com.atlantis.splitwise.model.entity;

import com.atlantis.splitwise.utils.enums.SplitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseInfo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    Long expenseInfoId;

    @Enumerated(EnumType.STRING)
    SplitType paidBySplitType;

    @Column
    String paidByInfo;

    @Column
    String paidAmounts;

    @Enumerated(EnumType.STRING)
    SplitType owedBySplitType;

    @Column
    String owedByInfo;

    @Column
    String owedAmounts;

    @Column
    Double totalAmount;

    @OneToOne
    Expense expense;

    // If versioning is introduced, then the relation will not be onetoone
    @Column
    Long version;

}
