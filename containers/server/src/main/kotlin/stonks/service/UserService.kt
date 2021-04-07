package stonks.service

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import stonks.model.Stock
import stonks.model.User
import stonks.repository.StockRepository
import stonks.repository.UserRepository

class UserService(
    private val stockService: StockService,
    private val stockRepository: StockRepository,
    private val userRepository: UserRepository
) {
    fun register(login: String): User {
        val user = User().apply { this.login = login }
        return userRepository.save(user)
    }

    fun deposit(login: String, money: Long) =
        findByLogin(login)
            .apply { this.money = this.money + money }
            .also { userRepository.save(it) }

    fun findMoney(login: String): Long {
        val user = findByLogin(login)
        return user.money + user.portfolio.sumOf { it.price }
    }

    fun buy(login: String, company: String, count: Long) {
        val user = findByLogin(login)
        val stock =
            stockService.findByCompany(company) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No such stock")
        val totalPrice = count * stock.price

        if (user.money < totalPrice) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds")
        if (count > stock.count) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Too many stocks to buy")

        user.money -= totalPrice
        stock.count -= count

        val portfolio = user.portfolio
        var stockExists = false
        portfolio.forEach {
            if (it.company == company) {
                it.count += count
                stockExists = true
                return@forEach
            }
        }

        if (!stockExists) {
            Stock()
                .apply {
                    this.count = count
                    this.company = company
                    this.price = stock.price
                }
                .also { portfolio += it }
        }
        stockRepository.save(stock)
        userRepository.save(user)
    }

    fun sell(login: String, company: String, count: Long) {
        val user = findByLogin(login)
        val stock = stockService.findByCompany(company) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val totalPrice = count * stock.price
        val portfolio = user.portfolio
        var stockExists = false
        portfolio.forEach {
            if (it.company == company) {
                if (it.count < count) throw ResponseStatusException(HttpStatus.BAD_REQUEST)

                it.count -= count
                stock.count += count
                user.money += totalPrice
                stockExists = true
            }
        }

        if (!stockExists) throw ResponseStatusException(HttpStatus.BAD_REQUEST)

        stockRepository.save(stock)
        userRepository.save(user)
    }

    fun findByLogin(login: String): User =
        userRepository.findByLogin(login) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No such user")

    fun getPortfolio(login: String): List<Stock> = findByLogin(login).portfolio
}