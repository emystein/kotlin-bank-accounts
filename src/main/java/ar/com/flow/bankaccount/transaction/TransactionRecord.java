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
    private final FundsMovement movement;
    private final Action action;
    private final Balance amount;

    public static TransactionRecord debit(BankAccount destinationAccount, Action action, Money amount) {
        return new TransactionRecord(destinationAccount,
                LocalDateTime.now(),
                FundsMovement.Debit,
                action,
                Balance.negative(amount)
        );
    }

    public static TransactionRecord credit(BankAccount destinationAccount, Action action, Money amount) {
        return new TransactionRecord(destinationAccount,
                LocalDateTime.now(),
                FundsMovement.Credit,
                action,
                Balance.positive(amount)
        );
    }
}
