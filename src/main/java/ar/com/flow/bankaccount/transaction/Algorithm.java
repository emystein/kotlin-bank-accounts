package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Algorithm {
    private final BankAccount account;
    private final ReceiptPrinter receiptPrinter;

    Receipt execute(Money amount) {
        Receipt receipt = receiptPrinter.receipt(amount);
        account.addTransactionRecord(receipt);
        return receipt;
    }

    Receipt undo(Money amount) {
        Receipt receipt = receiptPrinter.revert(amount);
        account.addTransactionRecord(receipt);
        return receipt;
    }
}
