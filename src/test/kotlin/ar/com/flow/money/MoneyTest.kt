package ar.com.flow.money

import ar.com.flow.money.Dollars.amount
import ar.com.flow.money.TestMoney.dollars10
import ar.com.flow.money.TestMoney.dollars100
import ar.com.flow.money.TestMoney.dollars110
import ar.com.flow.money.TestMoney.dollars90
import assertk.assertThat
import assertk.assertions.hasClass
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import org.junit.jupiter.api.Test

class MoneyTest {
    @Test
    fun givenANegativeAmountWhenCreateDollarsWithTheGivenAmountThenTheDollarsShouldNotBeCreated() {
        assertThat { amount(-10) }
            .isFailure()
            .hasClass(IllegalArgumentException::class)
    }

    @Test
    fun given100USDWhenAdd10USDThenResultShould110USD() {
        assertThat(dollars100.plus(dollars10)).isEqualTo(dollars110)
    }

    @Test
    fun given100USDWhenAdd10USDThen100USDShouldKeepOriginalAmount() {
        dollars100.plus(dollars10)
        assertThat(dollars100).isEqualTo(amount(100))
    }

    @Test
    fun given100USDWhenSubtract10USDThenResultShouldBe90USD() {
        assertThat(dollars100.minus(dollars10)).isEqualTo(dollars90)
    }

    @Test
    fun given100USDWhenSubtract200USDThenSubtractionShouldBeRejected() {
        assertThat { dollars100.minus(TestMoney.dollars200) }
            .isFailure()
            .hasClass(InsufficientFundsException::class)
    }

    @Test
    fun given100USDWhenSubtract10USDThen100USDShouldKeepOriginalAmount() {
        dollars100.minus(dollars10)
        assertThat(dollars100).isEqualTo(amount(100))
    }
}