package com.atlantis.splitwise.db;

import com.atlantis.splitwise.model.entity.Expense;
import com.atlantis.splitwise.model.entity.ExpenseSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, Long> {
    public List<ExpenseSplit> findByUserId(Long userId);
}