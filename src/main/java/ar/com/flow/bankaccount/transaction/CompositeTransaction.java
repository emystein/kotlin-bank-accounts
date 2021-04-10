package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Collection;

@AllArgsConstructor
@Builder
public class CompositeTransaction implements Transaction {
    @Builder.Default
    private Preconditions preconditions = new NoPreconditions();
    private final Collection<Algorithm> steps;
    private final Money amount;
    @Builder.Default
    private TransactionLog transactionLog  = new OnTransactionLog();

    public void execute() {
        preconditions.check();

        for (Algorithm step : steps) {
            var record = step.execute(amount);
            transactionLog.add(record, step.getAccount());
        }
    }
}
