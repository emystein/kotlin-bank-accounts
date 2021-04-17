package ar.com.flow.bankaccount.transaction.receipt;

import ar.com.flow.money.Money;

public interface ReceiptStamper {
    Receipt print(Money amount);

    Receipt scratch(Money amount);
}
