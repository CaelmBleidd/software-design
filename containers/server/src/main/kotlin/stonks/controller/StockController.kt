package stonks.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import stonks.model.Stock
import stonks.service.StockService

@RestController
@RequestMapping("/api/v1/stock")
class StockController(val stockService: StockService) {
    @PostMapping
    fun add(@RequestBody stock: Stock): Stock = stockService.add(stock)

    @GetMapping
    fun list() = stockService.list()

    @PutMapping("/{company}")
    fun updatePrice(@PathVariable company: String, @RequestParam price: Long) = stockService.updatePrice(company, price)
}