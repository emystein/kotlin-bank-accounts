package ar.com.flow.bankaccount;

import ar.com.flow.Customer;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.receipt.Receipt;
import ar.com.flow.bankaccount.withdrawal.CurrentFundsLimit;
import ar.com.flow.bankaccount.withdrawal.WithdrawalLimit;
import ar.com.flow.money.Money;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SavingsAccount implements BankAccount {
    private final Customer owner;
    private final String currency;
    @Builder.Default
    private WithdrawalLimit withdrawalLimit = new CurrentFundsLimit();
    @Builder.Default
    private Statement statement = new InMemoryStatement();

    public Balance getInitialBalance() {
        return statement.getInitialBalance()
                .orElse(Balance.zero(getCurrency()));
    }

    public Balance getBalance() {
        return statement.getCurrentBalance()
                .orElse(Balance.zero(getCurrency()));
    }

    public Balance getPreviousBalance() {
        return statement.getPreviousBalance()
                .orElse(Balance.zero(getCurrency()));
    }

    public boolean withdrawalLimitSupports(Money amount) {
        return withdrawalLimit.accepts(this, amount);
    }

    public void deposit(Money amountToDeposit) {
        Deposit.to(this)
                .amount(amountToDeposit)
                .execute();
    }

    public void withdraw(Money amountToWithdraw) {
        Withdrawal.from(this)
                .amount(amountToWithdraw)
                .execute();
    }

    public void transfer(Money amountToTransfer, BankAccount creditAccount) {
        Transfer.from(this)
                .to(creditAccount)
                .amount(amountToTransfer)
                .execute();
    }

    public void addReceipt(Receipt receipt) {
        statement.add(receipt);
    }
}
