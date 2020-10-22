package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.service.ProductService
import ru.akirakozov.sd.refactoring.util.createForSingle
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class QueryServlet : HttpServlet() {
    private val productService = ProductService()

    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        when (val command = request.getParameter("command")) {
            "max" -> {
                val productWithMaxPrice = productService.getProductWithMaxPrice()
                response.createForSingle(productWithMaxPrice, "<h1>Product with max price: </h1>")
            }
            "min" -> {
                val productWithMinPrice = productService.getProductWithMinPrice()
                response.createForSingle(productWithMinPrice, "<h1>Product with min price: </h1>")
            }
            "sum" -> {
                val totalPrice = productService.getTotalPrice()
                response.createForSingle(totalPrice, "Summary price: ")
            }
            "count" -> {
                val count = productService.getCount()
                response.createForSingle(count, "Number of products: ")
            }
            else -> {
                response.writer.println("Unknown command: $command")
            }
        }

    }
}