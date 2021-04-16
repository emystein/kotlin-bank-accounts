package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.ProofStamper;
import ar.com.flow.bankaccount.transaction.TransactionRecord;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransferDebitStamper implements ProofStamper {
    private final BankAccount creditAccount;

    public TransactionRecord record(Money amount) {
        return TransactionRecord.debit(creditAccount, Action.Transfer, amount);
    }

    public TransactionRecord revert(Money amount) {
        return TransactionRecord.credit(creditAccount, Action.Transfer, amount);
    }
}
