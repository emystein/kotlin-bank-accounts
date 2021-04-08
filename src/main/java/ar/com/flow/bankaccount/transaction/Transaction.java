package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.balance.Balance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    private final TransactionReason reason;
    private final BankAccount account;
    private final Balance amount;
    @Builder.Default
    private Preconditions preconditions = new NoPreconditions();
    @Builder.Default
    private Algorithm algorithm = new DoNothing();

    public void execute() {
        preconditions.check();

        algorithm.execute();

        account.addTransactionRecord(new TransactionRecord(now(), reason, amount));
    }
}
