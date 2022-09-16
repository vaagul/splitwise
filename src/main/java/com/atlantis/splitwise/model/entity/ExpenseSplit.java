package com.atlantis.splitwise.model.entity;

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
public class ExpenseSplit {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    Long splitId;

    @ManyToOne
    @JoinColumn(name="expense_id", nullable = false)
    Expense expense;

    @Column
    Long userId;

    @Column
    Double amount;

}
