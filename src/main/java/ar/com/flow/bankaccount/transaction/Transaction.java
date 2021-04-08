package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.balance.Balance;
import lombok.RequiredArgsConstructor;

import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
public class Transaction {
    private final BankAccount account;
    private final Preconditions preconditions;
    private final Algorithm algorithm;
    private final TransactionReason reason;
    private final Balance amount;

    public void execute() {
        preconditions.check();

        algorithm.execute();

        account.addTransactionRecord(new TransactionRecord(now(), reason, amount));
    }
}
