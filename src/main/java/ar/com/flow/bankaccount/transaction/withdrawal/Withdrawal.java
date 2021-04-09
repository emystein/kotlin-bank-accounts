package ar.com.flow.bankaccount.transaction.withdrawal;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.Transaction;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

public class Withdrawal {
    public static WithdrawalBuilder from(BankAccount debitAccount) {
        return new WithdrawalBuilder(debitAccount);
    }

    @RequiredArgsConstructor
    public static class WithdrawalBuilder {
        private Action reason = Action.Withdrawal;
        private final BankAccount debitAccount;
        private WithdrawalLimit withdrawalLimit = new CurrentFundsLimit();

        public WithdrawalBuilder limit(WithdrawalLimit aWithdrawalLimit) {
            withdrawalLimit = aWithdrawalLimit;

            return this;
        }

        public WithdrawalBuilder reason(Action aReason) {
            reason = aReason;
            return this;
        }

        public Transaction amount(Money amountToWithdraw) {
            return Transaction.builder()
                    .action(reason)
                    .account(debitAccount)
                    .amount(Balance.negative(amountToWithdraw))
                    .preconditions(new SufficientFunds(debitAccount, amountToWithdraw, withdrawalLimit))
                    .build();
        }
    }
}