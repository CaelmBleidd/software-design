package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.util.use
import java.sql.Connection
import java.sql.DriverManager
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GetProductsServlet : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        getConnection().use { c ->
            val stmt = c.createStatement()
            val rs = stmt.executeQuery("SELECT * FROM PRODUCT")
            response.writer.println("<html><body>")
            while (rs.next()) {
                val name = rs.getString("name")
                val price = rs.getInt("price")
                response.writer.println("$name\t$price</br>")
            }
            response.writer.println("</body></html>")
            rs.close()
            stmt.close()
        }

        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }

    fun getConnection(): Connection = DriverManager.getConnection("jdbc:sqlite:test.db")

}