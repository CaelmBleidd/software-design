package investor.model

data class Error(var status: Int = 0, var error: String? = null, var message: String? = null)

data class Stock(var company: String? = null, var price: Long = 0, var count: Long = 0)

data class User(var login: String? = null, var money: Long = 0, var portfolio: MutableList<Stock> = mutableListOf())