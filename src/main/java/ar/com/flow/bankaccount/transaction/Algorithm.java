package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Algorithm {
    private final BankAccount account;
    private final ProofStamper proofStamper;

    TransactionRecord execute(Money amount) {
        TransactionRecord record = proofStamper.record(amount);
        account.addTransactionRecord(record);
        return record;
    }

    void undo(Money amount) {
        account.addTransactionRecord(proofStamper.revert(amount));
    }
}
