package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DebitStamper implements ReceiptPrinter {
    private final BankAccount account;
    private final Action action;

    public Receipt print(Money amount) {
        return Receipt.debit(account, action, amount);
    }

    public Receipt scratch(Money amount) {
        return Receipt.credit(account, action, amount);
    }
}
