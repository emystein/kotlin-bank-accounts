package ar.com.flow.bankaccount.domain

import assertk.Assert
import assertk.assertions.support.expected

fun Assert<BankRegistry>.contains(aBank: Bank) = given { actual ->
    if (actual.contains(aBank)) return
    expected("Should contain bank $aBank")
}