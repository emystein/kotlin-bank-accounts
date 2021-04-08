package ar.com.flow.bankaccount.withdrawal;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;

public interface WithdrawalLimit {
    boolean accepts(BankAccount account, Money amount);
}
