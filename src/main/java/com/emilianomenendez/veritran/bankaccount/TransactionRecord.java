package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class TransactionRecord {
    private final LocalDateTime dateTime;
    private final Balance amount;

    public TransactionRecord(LocalDateTime dateTime, Money amount) {
        this.dateTime = dateTime;
        this.amount = Balance.create(amount);
    }

    public String getCurrency() {
        return amount.getCurrency();
    }
}
