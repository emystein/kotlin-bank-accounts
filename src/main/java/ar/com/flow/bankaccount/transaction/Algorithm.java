package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Algorithm {
    private final BankAccount account;
    private final ReceiptPrinter receiptPrinter;

    Receipt execute(Money amount) {
        return addToAccount(amount, new PrintReceipt(receiptPrinter));
    }

    Receipt undo(Money amount) {
        return addToAccount(amount, new ScratchReceipt(receiptPrinter));
    }

    Receipt addToAccount(Money amount, PrintableReceipt printable) {
        var receipt = printable.print(amount);
        account.addReceipt(receipt);
        return receipt;
    }
}

interface PrintableReceipt {
    Receipt print(Money amount);
}

@RequiredArgsConstructor
class PrintReceipt implements PrintableReceipt {
    private final ReceiptPrinter receiptPrinter;

    public Receipt print(Money amount) {
        return receiptPrinter.print(amount);
    }
}

@RequiredArgsConstructor
class ScratchReceipt implements PrintableReceipt {
    private final ReceiptPrinter receiptPrinter;

    public Receipt print(Money amount) {
        return receiptPrinter.scratch(amount);
    }
}
