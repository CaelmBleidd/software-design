package model

data class User(val login: String, val currency: Currency)

enum class Currency(name: String) {
    RUB("rub"),
    EUR("eur"),
    USD("usd")
}