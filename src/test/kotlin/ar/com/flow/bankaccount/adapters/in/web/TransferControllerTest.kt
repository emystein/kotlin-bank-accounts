package ar.com.flow.bankaccount.adapters.`in`.web

import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryPersistenceConfiguration
import ar.com.flow.bankaccount.application.spring.BankAccountConfiguration
import ar.com.flow.bankaccount.usecases.Transfer
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
@ContextConfiguration(
    classes = [BankAccountConfiguration::class, InMemoryPersistenceConfiguration::class]
)
class TransferControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var transfer: Transfer

    @Test
    fun transfer() {
        justRun { transfer.execute("Juan Perez", "ARS", 100, "Raúl Lopez") }

        val transferUrl = "/savings-accounts/transfer/{debitCustomer}/{currency}/{amount}/{creditCustomer}"

        mockMvc.perform(post(transferUrl, "Juan Perez", "ARS", 100, "Raúl Lopez"))
            .andExpect(status().isOk)
    }
}