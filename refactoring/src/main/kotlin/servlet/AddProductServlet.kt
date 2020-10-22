package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.service.ProductService
import ru.akirakozov.sd.refactoring.util.createEmpty
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class AddProductServlet : HttpServlet() {
    private val productService = ProductService()

    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val name = request.getParameter("name")
        val price = request.getParameter("price")

        productService.addProduct(name, price.toInt())
        response.createEmpty()
    }
}