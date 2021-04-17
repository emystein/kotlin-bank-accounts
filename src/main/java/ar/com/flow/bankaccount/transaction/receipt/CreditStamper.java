package ar.com.flow.bankaccount.transaction.receipt;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreditStamper implements ReceiptStamper {
    private final BankAccount account;
    private final Action action;

    public static CreditStamper deposit(BankAccount account) {
        return new CreditStamper(account, Action.Deposit);
    }

    public static CreditStamper transfer(BankAccount account) {
        return new CreditStamper(account, Action.Transfer);
    }

    public Receipt print(Money amount) {
        return Receipt.credit(account, action, amount);
    }

    public Receipt scratch(Money amount) {
        return Receipt.debit(account, action, amount);
    }
}
