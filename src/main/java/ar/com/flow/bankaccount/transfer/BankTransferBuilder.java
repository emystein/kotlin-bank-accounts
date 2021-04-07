package ar.com.flow.bankaccount.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BankTransferBuilder {
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
