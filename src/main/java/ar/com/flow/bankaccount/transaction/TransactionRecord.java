package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class TransactionRecord {
    private final BankAccount destinationAccount;
    private final LocalDateTime dateTime;
    private final Action action;
    private final Balance amount;

    public TransactionRecord(BankAccount destinationAccount, LocalDateTime dateTime, Action action, Money amount) {
        this(destinationAccount, dateTime, action, Balance.create(amount));
    }
}
