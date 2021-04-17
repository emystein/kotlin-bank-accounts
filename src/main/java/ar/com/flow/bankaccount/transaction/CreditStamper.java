package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreditStamper implements ReceipPrinter {
    private final BankAccount account;
    private final Action action;

    public Receipt receipt(Money amount) {
        return Receipt.credit(account, action, amount);
    }

    public Receipt revert(Money amount) {
        return Receipt.debit(account, action, amount);
    }
}
