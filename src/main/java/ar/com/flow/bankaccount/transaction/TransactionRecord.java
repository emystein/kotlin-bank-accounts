package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class TransactionRecord {
    private final LocalDateTime dateTime;
    private final TransactionReason reason;
    private final Balance balance;

    public TransactionRecord(LocalDateTime dateTime, TransactionReason reason, Money amount) {
        this.dateTime = dateTime;
        this.reason = reason;
        this.balance = Balance.create(amount);
    }
}
