package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.balance.Balance;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class Transaction {
    private final Action action;
    private final BankAccount account;
    private final Balance amount;
    @Builder.Default
    private Preconditions preconditions = new NoPreconditions();
    @Builder.Default
    private Algorithm algorithm = new DoNothing();

    public void execute() {
        preconditions.check();

        algorithm.execute();

        account.addTransactionRecord(TransactionRecord.now(action, amount));
    }
}
