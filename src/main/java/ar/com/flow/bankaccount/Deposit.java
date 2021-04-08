package ar.com.flow.bankaccount;

import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class Deposit extends BaseTransaction {
    private final TransactionReason reason;
    private final BankAccount creditAccount;
    private final Money amount;

    public Deposit(TransactionReason reason, BankAccount creditAccount, Money amount) {
        super(new NoPreconditions(), new DoNothing());

        this.reason = reason;
        this.creditAccount = creditAccount;
        this.amount = amount;
    }

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

        public Deposit amount(Money amountToDeposit) {
            return new Deposit(reason, creditAccount, amountToDeposit);
        }
    }

    public BankAccount account() {
        return creditAccount;
    }

    public TransactionRecord transactionRecord() {
        return transactionRecord(reason, Balance.positive(amount));
    }
}
