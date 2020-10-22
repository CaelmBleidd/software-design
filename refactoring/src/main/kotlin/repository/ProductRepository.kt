package ru.akirakozov.sd.refactoring.repository

import ru.akirakozov.sd.refactoring.model.Product
import ru.akirakozov.sd.refactoring.util.use
import java.sql.DriverManager.getConnection
import java.sql.ResultSet

class ProductRepository {
    private val url = "jdbc:sqlite:test.db"

    fun addProduct(product: Product) {
        val query = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"${product.name}\", ${product.price})"
        executeUpdate(query)
    }

    fun getAllProducts(): List<Product> {
        val query = "SELECT * FROM PRODUCT"
        val resultSet = executeQuery(query)

        val resultList = mutableListOf<Product>()
        while (resultSet.next()) {
            resultList += Product(resultSet.getString("name"), resultSet.getInt("price"))
        }
        return resultList.also { resultSet.close() }
    }

    fun getProductWithMaxPrice(): Product {
        val query = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1"
        val resultSet = executeQuery(query).also { it.next() }
        return Product(
            resultSet.getString("name"),
            resultSet.getInt("price")
        ).also { resultSet.close() }
    }

    fun getProductWithMinPrice(): Product {
        val query = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1"
        val resultSet = executeQuery(query).also { it.next() }
        return Product(
            resultSet.getString("name"),
            resultSet.getInt("price")
        ).also { resultSet.close() }
    }

    fun getTotalPrice(): Int {
        val query = "SELECT SUM(price) FROM PRODUCT"
        val resultSet = executeQuery(query).also { it.next() }
        return resultSet.getInt(1).also { resultSet.close() }
    }

    fun getCount(): Int {
        val query = "SELECT COUNT(*) FROM PRODUCT"
        val resultSet = executeQuery(query).also { it.next() }
        return resultSet.getInt(1).also { resultSet.close() }
    }

    private fun executeUpdate(query: String) {
        getConnection(url).use { connection ->
            connection.createStatement().use { statement ->
                statement.executeUpdate(query)
            }
        }
    }

    private fun executeQuery(query: String): ResultSet {
        return getConnection(url).use { connection ->
            connection.createStatement().use { statement ->
                return@use statement.executeQuery(query)
            }
        }
    }
}


