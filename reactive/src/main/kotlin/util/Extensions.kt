package util

import model.Currency

val coefficients = mapOf(
    Currency.USD to 1.0,
    Currency.EUR to 0.85,
    Currency.RUB to 76.01
)

fun convert(currency: Currency, value: Double) = coefficients.getValue(currency) * value

fun Map<String, List<String>>.getParameter(key: String, message: String): String {
    val results = get(key)

    if (results.isNullOrEmpty()) error(message)

    return results.first()
}