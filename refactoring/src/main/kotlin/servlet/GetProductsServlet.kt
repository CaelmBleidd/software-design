package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.service.ProductService
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class GetProductsServlet : HttpServlet() {
    private val productService = ProductService()

    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val products = productService.getAllProducts()
        response.writer.println("<html><body>")
        products.forEach { response.writer.println("${it.name}\t${it.price}</br>") }
        response.writer.println("</body></html>")

        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }
}