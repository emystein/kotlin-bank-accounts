package ar.com.flow

data class Customer(val name: String) {
    companion object {
        fun named(name: String): Customer {
            return Customer(name)
        }
    }
}