package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Money;

public interface SignedMoney extends Money {
    String getSign();
}
