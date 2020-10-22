import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet
import java.io.PrintWriter
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AddProductServletTest {
    private val request: HttpServletRequest = mockk()
    private val response: HttpServletResponse = mockk()
    private val printWriter: PrintWriter = mockk()
    private val connection: Connection = mockk()
    private val statement: Statement = mockk()

    @Before
    fun mockDriverManager() {
        mockkStatic(DriverManager::class)
        every { DriverManager.getConnection("jdbc:sqlite:test.db") } returns connection
    }

    @Test
    fun testAddProductDoGet() {
        every { response.writer } returns printWriter
        every { response.contentType = "text/html" } returns Unit
        every { response.status = HttpServletResponse.SC_OK } returns Unit
        every { printWriter.println("OK") } returns Unit
        every { request.getParameter("name") } returns "testItem"
        every { request.getParameter("price") } returns "500"
        every { connection.createStatement() } returns statement
        every { connection.close() } returns Unit
        every { statement.close() } returns Unit
        every { statement.executeUpdate("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"testItem\", 500)") } returns 1

        AddProductServlet().doGet(request, response)

        verify(exactly = 1) { response.writer }
        verify { response.contentType = "text/html" }
        verify { response.status = HttpServletResponse.SC_OK }
        verify { printWriter.println("OK") }
        confirmVerified(response)
    }
}
