package com.atlantis.splitwise.model.dto;

import com.atlantis.splitwise.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportLedger {
        User paidUser;
        User owedUser;
        Double amount;
}
