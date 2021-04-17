package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Algorithm {
    private final BankAccount account;
    private final ReceiptStamper receiptStamper;

    Receipt execute(Money amount) {
        return register(receiptStamper.print(amount));
    }

    Receipt undo(Money amount) {
        return register(receiptStamper.scratch(amount));
    }

    private Receipt register(Receipt receipt) {
        account.addReceipt(receipt);
        return receipt;
    }
}
