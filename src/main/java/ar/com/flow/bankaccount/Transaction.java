package ar.com.flow.bankaccount;

public interface Transaction {
    Balance getAmount();

    TransactionRecord execute();
}
