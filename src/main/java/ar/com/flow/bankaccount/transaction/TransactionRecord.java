package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.money.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class TransactionRecord {
    private final LocalDateTime dateTime;
    private final Action action;
    private final Balance amount;
    private Optional<BankAccount> creditAccount = Optional.empty();

    public TransactionRecord(LocalDateTime dateTime, Action action, Money amount) {
        this(dateTime, action, Balance.create(amount));
    }

    public TransactionRecord(LocalDateTime dateTime, Action action, Balance amount, BankAccount creditAccount) {
        this.dateTime = dateTime;
        this.action = action;
        this.amount = amount;
        this.creditAccount = Optional.of(creditAccount);
    }

    public static TransactionRecord now(Action action, Balance amount) {
        return new TransactionRecord(LocalDateTime.now(), action, amount);
    }

    public static TransactionRecord now(Action action, Balance amount, BankAccount creditAccount) {
        return new TransactionRecord(LocalDateTime.now(), action, amount, creditAccount);
    }
}
