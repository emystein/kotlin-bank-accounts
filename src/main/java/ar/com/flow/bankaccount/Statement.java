package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.Receipt;

import java.util.Optional;

public interface Statement {
    int total();

    Optional<Receipt> first();

    Optional<Receipt> last();

    void add(Receipt receipt);

    boolean contains(Receipt receipt);

    boolean containsInOrder(Receipt... records);

    Optional<Balance> getCurrentBalance();

    Optional<Balance> getPreviousBalance();

    Optional<Balance> getInitialBalance();
}
