package investor

import investor.command.AddStockCommand
import investor.command.AuthorizeCommand
import investor.command.BuyStocksCommand
import investor.command.DepositCommand
import investor.command.RegisterUserCommand
import investor.command.SellStocksCommand
import org.junit.Assert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.testcontainers.containers.FixedHostPortGenericContainer
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@Testcontainers
class StockExchangeTest {
    @Container
    private val ignored: GenericContainer<*> =
        FixedHostPortGenericContainer<FixedHostPortGenericContainer<*>>("docker.io/library/stonks:1.0")
            .withFixedExposedPort(8082, 8082)
            .withExposedPorts(8082)

    @Test
    fun canRegister() {
        val login = "login"
        val user = RegisterUserCommand(login).execute()
        Assert.assertEquals(login, user)
    }

    @Test
    fun canNotRegisterTwice() {
        val login = "login"
        val command = RegisterUserCommand(login)
        command.execute()
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }

    @Test
    fun canAuthorize() {
        val login = "login"
        val user = RegisterUserCommand(login).execute()
        val authorizedUser = AuthorizeCommand(login).execute()
        Assert.assertEquals(user, authorizedUser)
    }

    @Test
    fun canNotAuthorizeIfNotExists() {
        val login = "login"
        val command = AuthorizeCommand(login)
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }

    @Test
    fun canDeposit() {
        val login = "login"
        var user = RegisterUserCommand(login).execute()
        Assert.assertEquals(0L, user.money)
        val success = DepositCommand(login, 200L).execute()
        Assert.assertTrue(success)
        user = AuthorizeCommand(login).execute()
        Assert.assertEquals(200L, user.money)
    }

    @Test
    fun canNotDepositNotRegistered() {
        val login = "login"
        val command = DepositCommand(login, 200L)
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }

    @Test
    fun canNotDepositInvalidMoneyValue() {
        val login = "login"
        val user = RegisterUserCommand(login).execute()
        Assert.assertEquals(0L, user.money)
        val command = DepositCommand(login, -200L)
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }

    @Test
    fun canNotDepositMoneyOverflow() {
        val login = "login"
        val user = RegisterUserCommand(login).execute()
        Assert.assertEquals(0L, user.money)
        val command = DepositCommand(login, 50001L)
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }

    @Test
    fun canAddStock() {
        val company = "company"
        val stock = AddStockCommand(company, 1L, 1L).execute()
        Assert.assertEquals(company, stock)
    }

    @Test
    fun canNotAddStockTwice() {
        val company = "company"
        val command = AddStockCommand(company, 1L, 1L)
        command.execute()
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }

    @Test
    fun canNotAddStockInvalidPrice() {
        val company = "company"
        val command = AddStockCommand(company, -1L, 1L)
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }

    @Test
    fun canNotAddStockInvalidCount() {
        val company = "company"
        val command = AddStockCommand(company, 1L, -1L)
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }

    @Test
    fun canNotBuyStockNotRegistered() {
        val company = "company"
        val login = "login"
        AddStockCommand(company, 1L, 1L).execute()
        val command = BuyStocksCommand(login, company, 1L)
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }

    @Test
    fun canNotBuyStockIfNotExists() {
        val company = "company"
        val login = "login"
        val command = BuyStocksCommand(login, company, 1L)
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }

    @Test
    fun canNotBuyStockInsufficientFunds() {
        val company = "company"
        val login = "login"
        AddStockCommand(company, 1L, 1L).execute()
        RegisterUserCommand(login).execute()
        val command = BuyStocksCommand(login, company, 1L)
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }

    @Test
    fun canNotBuyStockNotEnoughStock() {
        val company = "company"
        val login = "login"
        AddStockCommand(company, 1L, 1L).execute()
        RegisterUserCommand(login).execute()
        val success = DepositCommand(login, 1L).execute()
        Assert.assertTrue(success)
        val command = BuyStocksCommand(login, company, 2L)
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }

    @Test
    fun canNotSellStockNotRegistered() {
        val company = "company"
        val login = "login"
        AddStockCommand(company, 1L, 1L).execute()
        val command = SellStocksCommand(login, company, 1L)
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }

    @Test
    fun canNotSellStockIfNotExists() {
        val company = "company"
        val login = "login"
        val command = SellStocksCommand(login, company, 1L)
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }

    @Test
    fun canNotSellStockNotEnoughStock() {
        val company = "company"
        val login = "login"
        AddStockCommand(company, 1L, 1L).execute()
        RegisterUserCommand(login).execute()
        val command = SellStocksCommand(login, company, 1L)
        Assertions.assertThrows(RuntimeException::class.java) { command.execute() }
    }
}
