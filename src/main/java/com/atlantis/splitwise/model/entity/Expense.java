package com.atlantis.splitwise.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    Long expenseId;

    @Column
    String addedBy;

    @Column
    String label;

    @Column
    String description;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    Date updatedAt;

    @OneToMany
    Set<ExpenseSplit> expenseSplit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "expense_info_id")
    ExpenseInfo expenseInfo;
}
