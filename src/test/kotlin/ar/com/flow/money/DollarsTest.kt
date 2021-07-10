package ar.com.flow.money

import ar.com.flow.money.Dollars.Companion.amount
import ar.com.flow.money.TestObjects.dollars100
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class DollarsTest {
    @Test
    fun givenAPositiveAmountWhenCreateDollarsWithTheGivenAmountThenTheDollarsShouldBeCreated() {
        val dollars = amount(10)
        assertEquals("USD", dollars.currency)
        assertEquals(10, dollars.amount)
    }

    @Test
    fun givenANegativeAmountWhenCreateDollarsWithTheGivenAmountThenTheDollarsShouldNotBeCreated() {
        assertThrows(IllegalArgumentException::class.java) { amount(-10) }
    }

    @Test
    fun given100USDWhenAdd10USDThenResultShould110USD() {
        assertEquals(TestObjects.dollars110, dollars100.plus(TestObjects.dollars10))
    }

    @Test
    fun given100USDWhenAdd10USDThen100USDShouldKeepOriginalAmount() {
        dollars100.plus(TestObjects.dollars10)
        assertEquals(amount(100), dollars100)
    }

    @Test
    fun given100USDWhenSubtract10USDThenResultShouldBe90USD() {
        assertEquals(TestObjects.dollars90, dollars100.minus(TestObjects.dollars10))
    }

    @Test
    fun given100USDWhenSubtract200USDThenSubtractionShouldBeRejected() {
        assertThrows(InsufficientFundsException::class.java) { dollars100.minus(TestObjects.dollars200) }
    }

    @Test
    fun given100USDWhenSubtract10USDThen100USDShouldKeepOriginalAmount() {
        dollars100.minus(TestObjects.dollars10)

        assertEquals(amount(100), dollars100)
    }
}