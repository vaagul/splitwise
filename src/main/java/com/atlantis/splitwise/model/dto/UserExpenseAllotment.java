package com.atlantis.splitwise.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserExpenseAllotment {
    Long userId;
    Double amount;
}
