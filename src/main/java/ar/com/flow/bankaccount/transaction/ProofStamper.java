package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;

public interface ProofStamper {
    TransactionRecord record(Money amount);

    TransactionRecord revert(Money amount);
}
