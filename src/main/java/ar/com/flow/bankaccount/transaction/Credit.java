package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Credit implements Algorithm {
    private final BankAccount account;
    private final Action action;

    public TransactionRecord execute(Money amount) {
        return TransactionRecord.credit(account, action, amount);
    }
}
