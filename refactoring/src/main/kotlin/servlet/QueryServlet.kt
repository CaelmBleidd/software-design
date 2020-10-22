package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.service.ProductService
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class QueryServlet : HttpServlet() {
    private val productService = ProductService()

    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        when (val command = request.getParameter("command")) {
            "max" -> {
                val productWithMaxPrice = productService.getProductWithMaxPrice()
                response.writer.println("<html><body>")
                response.writer.println("<h1>Product with max price: </h1>")
                response.writer.println("${productWithMaxPrice.name}\t${productWithMaxPrice.price}</br>")
                response.writer.println("</body></html>")

            }
            "min" -> {
                val productWithMinPrice = productService.getProductWithMinPrice()
                response.writer.println("<html><body>")
                response.writer.println("<h1>Product with min price: </h1>")
                response.writer.println("${productWithMinPrice.name}\t${productWithMinPrice.price}</br>")
                response.writer.println("</body></html>")
            }
            "sum" -> {
                val totalPrice = productService.getTotalPrice()
                response.writer.println("<html><body>")
                response.writer.println("Summary price: ")
                response.writer.println(totalPrice)
                response.writer.println("</body></html>")
            }
            "count" -> {
                val count = productService.getCount()
                response.writer.println("<html><body>")
                response.writer.println("Number of products: ")
                response.writer.println(count)
                response.writer.println("</body></html>")
            }
            else -> {
                response.writer.println("Unknown command: $command")
            }
        }
        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }
}