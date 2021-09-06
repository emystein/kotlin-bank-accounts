package ar.com.flow.bankaccount.adapters.`in`.web

import ar.com.flow.bankaccount.application.spring.BankAccountConfiguration
import ar.com.flow.bankaccount.usecases.Deposit
import com.ninjasquad.springmockk.MockkBean
import io.mockk.justRun
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest
@ContextConfiguration(classes = [BankAccountConfiguration::class])
class DepositControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var deposit: Deposit

    @Test
    internal fun deposit() {
        justRun { deposit.deposit("Juan Perez", "ARS", 100) }

        val depositUrl = "/savings-accounts/deposit/{customerName}/{currency}/{amount}"

        mockMvc.perform(post(depositUrl, "Juan Perez", "ARS", 100))
            .andExpect(status().isOk)
    }
}