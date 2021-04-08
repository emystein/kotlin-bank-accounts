package ar.com.flow.bankaccount.withdrawal;

import ar.com.flow.bankaccount.*;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

public class Withdrawal {
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

        public Transaction amount(Money amountToWithdraw) {
            return new Transaction(debitAccount,
                    new SuffificientFunds(debitAccount, amountToWithdraw, withdrawalLimit),
                    new DoNothing(),
                    reason,
                    Balance.negative(amountToWithdraw));
        }
    }
}
