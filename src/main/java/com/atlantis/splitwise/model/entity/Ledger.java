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
public class Ledger {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    Long ledgerId;

    @Column
    Long paidUserId;

    @Column
    Long owedUserId;

    @Column
    Double amount;
}
