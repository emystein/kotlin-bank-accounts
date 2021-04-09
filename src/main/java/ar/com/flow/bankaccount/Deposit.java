package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.Transaction;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

public class Deposit {
    public static DepositBuilder to(BankAccount creditAccount) {
        return new DepositBuilder(creditAccount);
    }

    @RequiredArgsConstructor
    public static class DepositBuilder {
        private Action reason = Action.Deposit;
        private final BankAccount creditAccount;

        public DepositBuilder reason(Action aReason) {
            reason = aReason;
            return this;
        }

        public Transaction amount(Money amountToDeposit) {
            return Transaction.builder()
                    .action(reason)
                    .account(creditAccount)
                    .amount(Balance.positive(amountToDeposit))
                    .build();
        }
    }
}
