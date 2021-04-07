package stonks.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import stonks.model.Stock
import stonks.repository.StockRepository

@Service
class StockService(val stockRepository: StockRepository) {

    fun add(stock: Stock): Stock = stockRepository.save(stock)

    fun list(): List<Stock> = stockRepository.findAll()

    fun updatePrice(company: String, price: Long): Stock {
        val stock = stockRepository.findByCompany(company) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "No such stock"
        )

        stock.price = price
        return stockRepository.save(stock)
    }

    fun findByCompany(company: String): Stock? = stockRepository.findByCompany(company)
}