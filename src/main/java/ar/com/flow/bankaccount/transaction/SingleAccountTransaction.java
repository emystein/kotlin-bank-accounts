package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class SingleAccountTransaction implements Transaction {
    private final Action action;
    private final BankAccount account;
    private final Money amount;
    private final Algorithm algorithm;
    @Builder.Default
    private Preconditions preconditions = new NoPreconditions();
    @Builder.Default
    private Recorder recorder = new ConcreteRecorder();

    public void execute() {
        preconditions.check();

        var record = algorithm.execute(amount);

        recorder.add(record, account);
    }
}
