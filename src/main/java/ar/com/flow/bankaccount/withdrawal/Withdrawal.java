package ar.com.flow.bankaccount.withdrawal;

import ar.com.flow.bankaccount.*;
import ar.com.flow.money.InsufficientFundsException;
import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class Withdrawal extends BaseTransaction {
    private final TransactionReason reason;
    private final BankAccount debitAccount;
    private final Money amount;
    private final WithdrawalLimit withdrawalLimit;

    public Withdrawal(TransactionReason reason, BankAccount debitAccount, Money amount, WithdrawalLimit withdrawalLimit) {
        super(new SuffificientFunds(debitAccount, amount, withdrawalLimit), new DoNothing());

        this.reason = reason;
        this.debitAccount = debitAccount;
        this.amount = amount;
        this.withdrawalLimit = withdrawalLimit;
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

    public Balance previewBalanceAfter() {
        return debitAccount.getBalance().minus(amount);
    }

    public BankAccount account() {
        return debitAccount;
    }

    public TransactionRecord transactionRecord() {
        return transactionRecord(reason, Balance.negative(amount));
    }
}
