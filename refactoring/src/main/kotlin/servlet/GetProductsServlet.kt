package ru.akirakozov.sd.refactoring.servlet

import java.sql.DriverManager
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import ru.akirakozov.sd.refactoring.util.use

open class GetProductsServlet : HttpServlet() {
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        DriverManager.getConnection("jdbc:sqlite:test.db").use { c ->
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
}