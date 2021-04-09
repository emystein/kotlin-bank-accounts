package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.*;
import ar.com.flow.bankaccount.transaction.withdrawal.Withdrawal;
import ar.com.flow.money.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class TransferTransaction {
    private final Action action;
    private final BankAccount debitAccount;
    private final BankAccount creditAccount;
    private final Money amount;
    private final Algorithm algorithm;
    private final Preconditions preconditions;

    public void execute() {
        preconditions.check();

        Withdrawal.from(debitAccount)
                .limit(debitAccount.getWithdrawalLimit())
                .amount(amount)
                .recorder(new NoRecorder())
                .build()
                .execute();

        debitAccount.addTransactionRecord(TransactionRecord.now(Action.Transfer, Balance.negative(amount), creditAccount));

        Deposit.to(creditAccount)
                .amount(amount)
                .execute();

        creditAccount.addTransactionRecord(TransactionRecord.now(Action.Transfer, Balance.positive(amount)));
    }
}
