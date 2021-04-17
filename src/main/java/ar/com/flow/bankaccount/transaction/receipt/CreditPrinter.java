package ar.com.flow.bankaccount.transaction.receipt;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreditPrinter implements ReceiptPrinter {
    private final BankAccount account;
    private final Action action;

    public Receipt print(Money amount) {
        return Receipt.credit(account, action, amount);
    }

    public Receipt scratch(Money amount) {
        return Receipt.debit(account, action, amount);
    }
}
