package ru.akirakozov.sd.refactoring.repository

import java.sql.DriverManager.getConnection
import java.sql.ResultSet
import ru.akirakozov.sd.refactoring.model.Product
import ru.akirakozov.sd.refactoring.util.use

class ProductRepository {
    private val url = "jdbc:sqlite:test.db"

    fun addProduct(product: Product) {
        val query = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"${product.name}\", ${product.price})"
        executeUpdate(query)
    }

    fun getAllProducts(): List<Product> {
        val query = "SELECT * FROM PRODUCT"
        return executeQuery(query) {
            val resultList = mutableListOf<Product>()
            while (it.next()) {
                resultList += it.extractProduct()
            }
            resultList
        }
    }

    fun getProductWithMaxPrice(): Product {
        val query = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1"
        return executeQuery(query) {
            it.next()
            it.extractProduct()
        }
    }

    fun getProductWithMinPrice(): Product {
        val query = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1"
        return executeQuery(query) {
            it.next()
            it.extractProduct()
        }
    }

    fun getTotalPrice(): Int {
        val query = "SELECT SUM(price) FROM PRODUCT"
        return executeQuery(query) { it.getInt(1) }
    }

    fun getCount(): Int {
        val query = "SELECT COUNT(*) FROM PRODUCT"
        return executeQuery(query) { it.getInt(1) }
    }

    private fun executeUpdate(query: String) {
        getConnection(url).use { connection ->
            connection.createStatement().use { statement ->
                statement.executeUpdate(query)
            }
        }
    }

    private fun ResultSet.extractProduct() = Product(getString("name"), getInt("price"))

    private fun <T> executeQuery(query: String, block: (ResultSet) -> T): T {
        return getConnection(url).use { connection ->
            connection.createStatement().use { statement ->
                val resultSet = statement.executeQuery(query)
                return@use block(resultSet).also { resultSet.close() }
            }
        }
    }
}


