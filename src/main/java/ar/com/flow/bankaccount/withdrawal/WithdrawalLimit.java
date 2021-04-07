package ar.com.flow.bankaccount.withdrawal;

public interface WithdrawalLimit {
    boolean accepts(Withdrawal withdrawal);
}
