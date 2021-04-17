package ar.com.flow.bankaccount.transaction.receipt;

import ar.com.flow.money.Money;

public interface ReceiptPrinter {
    Receipt print(Money amount);

    Receipt scratch(Money amount);
}
