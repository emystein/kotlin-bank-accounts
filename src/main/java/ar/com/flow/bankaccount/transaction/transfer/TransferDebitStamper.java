package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.ReceiptPrinter;
import ar.com.flow.bankaccount.transaction.Receipt;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransferDebitStamper implements ReceiptPrinter {
    private final BankAccount creditAccount;

    public Receipt print(Money amount) {
        return Receipt.debit(creditAccount, Action.Transfer, amount);
    }

    public Receipt scratch(Money amount) {
        return Receipt.credit(creditAccount, Action.Transfer, amount);
    }
}
