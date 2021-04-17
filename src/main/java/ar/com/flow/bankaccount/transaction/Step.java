package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.receipt.Receipt;
import ar.com.flow.bankaccount.transaction.receipt.ReceiptPrinter;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Step {
    private final BankAccount account;
    private final ReceiptPrinter receiptPrinter;

    Receipt execute(Money amount) {
        return register(receiptPrinter.print(amount));
    }

    Receipt undo(Money amount) {
        return register(receiptPrinter.scratch(amount));
    }

    private Receipt register(Receipt receipt) {
        account.addReceipt(receipt);
        return receipt;
    }
}
