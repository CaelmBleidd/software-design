package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.service.ProductService
import ru.akirakozov.sd.refactoring.util.createForCollection
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class GetProductsServlet : HttpServlet() {
    private val productService = ProductService()

    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val products = productService.getAllProducts()
        response.createForCollection(products)
    }
}