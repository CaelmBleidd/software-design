package investor.command

import investor.model.Stock
import investor.model.User
import investor.util.checkResponseCode
import investor.util.httpClient
import investor.util.objectMapper
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import lombok.SneakyThrows


interface Command<T> {
    fun execute(): T

    fun getAddress(): String

    fun getBaseUrl(): String

    fun getUri(): URI = URI.create("http://localhost:8082${getBaseUrl()}${getAddress()}")
}

sealed class StockCommand<T>(val company: String) : Command<T> {
    override fun getBaseUrl(): String = "/api/v1/stock"
}

sealed class UserCommand<T>(val login: String) : Command<T> {
    override fun getBaseUrl(): String = "/api/v1/user"
}

class AddStockCommand(
    company: String,
    private val price: Long,
    private val count: Long
) : StockCommand<Stock>(company) {
    override fun getAddress() = ""

    override fun execute(): Stock {
        val stock = Stock(company, price, count)
        val request = HttpRequest
            .newBuilder()
            .uri(getUri())
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(stock)))
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        checkResponseCode(response)
        return objectMapper.readerFor(Stock::class.java).readValue(response.body())
    }
}

class RegisterUserCommand(login: String) : UserCommand<User>(login) {
    override fun getAddress() = "?login=$login"

    @SneakyThrows
    override fun execute(): User {
        val request = HttpRequest
            .newBuilder()
            .uri(getUri())
            .POST(HttpRequest.BodyPublishers.noBody())
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        checkResponseCode(response)
        return objectMapper.readerFor(User::class.java).readValue(response.body())
    }
}


class SellStocksCommand(
    login: String,
    private val company: String,
    private val count: Long
) : UserCommand<Boolean>(login) {
    override fun getAddress() = "/sell/${login}?company=${company}&count=${count}"

    @SneakyThrows
    override fun execute(): Boolean {
        val request = HttpRequest
            .newBuilder()
            .uri(getUri())
            .PUT(HttpRequest.BodyPublishers.noBody())
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        checkResponseCode(response)
        return true
    }
}

class GetPortfolioCommand(login: String) : UserCommand<List<Stock>>(login) {
    override fun getAddress() = "/portfolio/${login}"

    @SneakyThrows
    override fun execute(): List<Stock> {
        val request = HttpRequest
            .newBuilder()
            .uri(getUri())
            .GET()
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        checkResponseCode(response)
        return objectMapper.readerFor(Array<Stock>::class.java).readValue(response.body())
    }
}

class GetMoneyCommand(login: String) : UserCommand<Long>(login) {
    override fun getAddress() = "/money/${login}"

    @SneakyThrows
    override fun execute(): Long {
        val request = HttpRequest
            .newBuilder()
            .uri(getUri())
            .GET()
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        checkResponseCode(response)
        return objectMapper.readerFor(Long::class.java).readValue(response.body())
    }
}

class DepositCommand(login: String, private val money: Long) : UserCommand<Boolean>(login) {
    override fun getAddress() = "/deposit/${login}?value=${money}"

    @SneakyThrows
    override fun execute(): Boolean {
        val request = HttpRequest
            .newBuilder()
            .uri(getUri())
            .PUT(HttpRequest.BodyPublishers.noBody())
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        checkResponseCode(response)
        return true
    }
}

class BuyStocksCommand(
    login: String,
    private val company: String,
    private val count: Long
) : UserCommand<Boolean>(login) {
    override fun getAddress() = "/buy/${login}?company=${company}&count=${count}"

    @SneakyThrows
    override fun execute(): Boolean {
        val request = HttpRequest
            .newBuilder()
            .uri(getUri())
            .PUT(HttpRequest.BodyPublishers.noBody())
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        checkResponseCode(response)
        return true
    }
}

class AuthorizeCommand(login: String) : UserCommand<User>(login) {
    override fun getAddress() = "/${login}"

    @SneakyThrows
    override fun execute(): User {
        val request = HttpRequest
            .newBuilder()
            .uri(getUri())
            .GET()
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        checkResponseCode(response)
        return objectMapper.readerFor(User::class.java).readValue(response.body())
    }
}


class UpdatePriceCommand(company: String, private val price: Long) : StockCommand<Stock>(company) {
    override fun getAddress() = "/${company}?price=${price}"

    @SneakyThrows
    override fun execute(): Stock {
        val request = HttpRequest
            .newBuilder()
            .uri(getUri())
            .PUT(HttpRequest.BodyPublishers.noBody())
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        checkResponseCode(response)
        return objectMapper.readerFor(Stock::class.java).readValue(response.body())
    }
}

class ListStockCommand(company: String) : StockCommand<List<Stock>>(company) {
    override fun getAddress() = ""

    @SneakyThrows
    override fun execute(): List<Stock> {
        val request = HttpRequest
            .newBuilder()
            .uri(getUri())
            .GET()
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        checkResponseCode(response)
        return objectMapper.readerFor(Array<Stock>::class.java).readValue(response.body())
    }
}