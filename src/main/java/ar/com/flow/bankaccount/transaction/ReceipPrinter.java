package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;

public interface ReceipPrinter {
    Receipt receipt(Money amount);

    Receipt revert(Money amount);
}
