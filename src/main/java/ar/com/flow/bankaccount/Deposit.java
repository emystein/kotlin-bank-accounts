package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.DoNothing;
import ar.com.flow.bankaccount.transaction.NoPreconditions;
import ar.com.flow.bankaccount.transaction.Transaction;
import ar.com.flow.bankaccount.transaction.TransactionReason;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

public class Deposit {
    public static DepositBuilder to(BankAccount creditAccount) {
        return new DepositBuilder(creditAccount);
    }

    @RequiredArgsConstructor
    public static class DepositBuilder {
        private TransactionReason reason = TransactionReason.Deposit;
        private final BankAccount creditAccount;

        public DepositBuilder reason(TransactionReason aReason) {
            reason = aReason;
            return this;
        }

        public Transaction amount(Money amountToDeposit) {
            return new Transaction(creditAccount,
                    new NoPreconditions(),
                    new DoNothing(),
                    reason,
                    Balance.positive(amountToDeposit));
        }
    }
}
