package ru.akirakozov.sd.refactoring.repository

import ru.akirakozov.sd.refactoring.model.Product
import java.sql.DriverManager

class ProductRepository {
    val url = "jdbc:sqlite:test.db"
    val connection = DriverManager.getConnection(url)

    fun addProduct(product: Product) {
        val query = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"${product.name}\", ${product.price})"
        val statement = connection.createStatement()
        statement.executeUpdate(query)
        statement.close()
    }

    fun getAllProducts(): List<Product> {
        val query = "SELECT * FROM PRODUCT"
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(query)
        val resultList = mutableListOf<Product>()
        while (resultSet.next()) {
            resultList += Product(resultSet.getString("name"), resultSet.getInt("price"))
        }
        resultSet.close()
        statement.close()
        return resultList
    }

    fun getProductWithMaxPrice(): Product {
        val query = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1"
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(query)
        return resultSet.next().let {
            Product(resultSet.getString("name"), resultSet.getInt("price"))
        }
    }

    fun getProductWithMinPrice(): Product {
        val query = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1"
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(query)
        resultSet.next()
        return Product(resultSet.getString("name"), resultSet.getInt("price"))
    }

    fun getTotalPrice(): Int {
        val query = "SELECT SUM(price) FROM PRODUCT"
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(query)
        resultSet.next()
        return resultSet.getInt(1)
    }

    fun getCount(): Int {
        val query = "SELECT COUNT(*) FROM PRODUCT"
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(query)
        resultSet.next()
        return resultSet.getInt(1)
    }
}