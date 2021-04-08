package ar.com.flow.bankaccount.transfer;

import ar.com.flow.bankaccount.*;
import ar.com.flow.bankaccount.withdrawal.Withdrawal;
import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.time.LocalDateTime.now;

@Getter
public class BankTransfer extends BaseTransaction {
    private final BankAccount debitAccount;
    private final BankAccount creditAccount;
    private final Money amount;

    public BankTransfer(BankAccount debitAccount, BankAccount creditAccount, Money amount) {
        super(new DifferentAccounts(debitAccount, creditAccount));

        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
    }

    public static BankTransferBuilder from(BankAccount debitAccount) {
        return new BankTransferBuilder(debitAccount);
    }

    @RequiredArgsConstructor
    public static class BankTransferBuilder {
        private final BankAccount debitAccount;
        private BankAccount creditAccount;

        public BankTransferBuilder to(BankAccount creditAccount) {
            this.creditAccount = creditAccount;
            return this;
        }

        public BankTransfer amount(Money amountToTransfer) {
            return new BankTransfer(debitAccount, creditAccount, amountToTransfer);
        }
    }

    public BankAccount account() {
        return creditAccount;
    }

    public void executeSpecific() {
        Withdrawal.from(debitAccount)
                .reason(TransactionReason.TransferDebit)
                .limit(debitAccount.getWithdrawalLimit())
                .amount(amount)
                .execute();

        Deposit.to(creditAccount)
                .reason(TransactionReason.TransferCredit)
                .amount(amount)
                .execute();
    }

    public TransactionRecord transactionRecord() {
        return new TransactionRecord(now(), TransactionReason.TransferCredit, Balance.positive(amount));
    }
}
