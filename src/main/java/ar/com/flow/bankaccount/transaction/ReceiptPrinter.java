package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;

public interface ReceiptPrinter {
    Receipt print(Money amount);

    Receipt scratch(Money amount);
}
