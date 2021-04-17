package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;

public interface ReceiptPrinter {
    Receipt record(Money amount);

    Receipt revert(Money amount);
}
