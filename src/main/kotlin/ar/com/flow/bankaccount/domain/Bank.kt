package ar.com.flow.bankaccount.domain

data class Bank(private val name: String, private val code: String) {
    companion object {
        fun startCreate(): BankBuilder {
            return BankBuilder()
        }
    }
}

class BankBuilder {
    private var name: String = ""
    private var code: String = ""

    fun withName(aName: String): BankBuilder {
        this.name = aName
        return this
    }

    fun withCode(aCode: String): BankBuilder {
        this.code = aCode
        return this
    }

    fun create(): Bank {
        return Bank(name=this.name, code=this.code)
    }
}
