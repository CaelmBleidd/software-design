package ru.akirakozov.sd.refactoring.service

import ru.akirakozov.sd.refactoring.model.Product
import ru.akirakozov.sd.refactoring.repository.ProductRepository

class ProductService {
    private val productRepository = ProductRepository()

    fun addProduct(name: String, price: Int) {
        val product = Product(name, price)
        productRepository.addProduct(product)
    }

    fun getAllProducts() = productRepository.getAllProducts()

    fun getProductWithMaxPrice() = productRepository.getProductWithMaxPrice()

    fun getProductWithMinPrice() = productRepository.getProductWithMinPrice()

    fun getTotalPrice() = productRepository.getTotalPrice()

    fun getCount() = productRepository.getCount()
}