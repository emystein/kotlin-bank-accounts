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
    private final Action action;
    private final Balance balance;

    public TransactionRecord(LocalDateTime dateTime, Action action, Money amount) {
        this(dateTime, action, Balance.create(amount));
    }

    public static TransactionRecord now(Action action, Balance amount) {
        return new TransactionRecord(LocalDateTime.now(), action, amount);
    }
}
