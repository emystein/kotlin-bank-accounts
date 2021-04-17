package ar.com.flow.bankaccount.transaction.receipt;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class Receipt {
    private final BankAccount destinationAccount;
    private final LocalDateTime dateTime;
    private final FundsMovement movement;
    private final Action action;
    private final Balance amount;
    private final Balance resultBalance;

    public static Receipt debit(BankAccount destinationAccount, Action action, Money amount) {
        Balance balance = Balance.negative(amount);

        return new Receipt(destinationAccount,
                LocalDateTime.now(),
                FundsMovement.Debit,
                action,
                balance,
                destinationAccount.getBalance().plus(balance)
        );
    }

    public static Receipt credit(BankAccount destinationAccount, Action action, Money amount) {
        Balance positive = Balance.positive(amount);

        return new Receipt(destinationAccount,
                LocalDateTime.now(),
                FundsMovement.Credit,
                action,
                positive,
                destinationAccount.getBalance().plus(positive)
        );
    }
}
