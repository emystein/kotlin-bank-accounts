package ar.com.flow.bankaccount.withdrawal;

import ar.com.flow.bankaccount.*;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

public class Withdrawal extends Transaction {
    public Withdrawal(TransactionReason reason, BankAccount debitAccount, Money amount, WithdrawalLimit withdrawalLimit) {
        super(debitAccount,
                new SuffificientFunds(debitAccount, amount, withdrawalLimit),
                new DoNothing(),
                reason,
                Balance.negative(amount));
    }

    public static WithdrawalBuilder from(BankAccount debitAccount) {
        return new WithdrawalBuilder(debitAccount);
    }

    @RequiredArgsConstructor
    public static class WithdrawalBuilder {
        private TransactionReason reason = TransactionReason.Withdrawal;
        private final BankAccount debitAccount;
        private WithdrawalLimit withdrawalLimit = new CurrentFundsLimit();

        public WithdrawalBuilder limit(WithdrawalLimit aWithdrawalLimit) {
            withdrawalLimit = aWithdrawalLimit;

            return this;
        }

        public WithdrawalBuilder reason(TransactionReason aReason) {
            reason = aReason;
            return this;
        }

        public Withdrawal amount(Money amountToWithdraw) {
            return new Withdrawal(reason, debitAccount, amountToWithdraw, withdrawalLimit);
        }
    }
}
