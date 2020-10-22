import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import ru.akirakozov.sd.refactoring.servlet.QueryServlet
import java.io.PrintWriter
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class QueryServletTest {
    private val request: HttpServletRequest = mockk()
    private val response: HttpServletResponse = mockk()
    private val printWriter: PrintWriter = mockk()
    private val connection: Connection = mockk()
    private val statement: Statement = mockk()
    private val resultSet: ResultSet = mockk()

    @Before
    fun mockDriverManager() {
        mockkStatic(DriverManager::class)
        every { DriverManager.getConnection("jdbc:sqlite:test.db") } returns connection
    }

    @Test
    fun testDoGetMax() {
        mock("max", "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1")

        QueryServlet().doGet(request, response)

        verify(exactly = 1) { printWriter.println("testItem\t500</br>") }
        verify(exactly = 4) { response.writer }
        verify { response.contentType = "text/html" }
        verify { response.status = HttpServletResponse.SC_OK }
        confirmVerified(response)
    }

    @Test
    fun testDoGetMin() {
        mock("min", "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1")

        QueryServlet().doGet(request, response)

        verify(exactly = 1) { printWriter.println("testItem\t500</br>") }
        verify(exactly = 4) { response.writer }
        verify { response.contentType = "text/html" }
        verify { response.status = HttpServletResponse.SC_OK }
        confirmVerified(response)
    }

    @Test
    fun testDoGetSum() {
        mock("sum", "SELECT SUM(price) FROM PRODUCT", 500)

        QueryServlet().doGet(request, response)

        verify(exactly = 1) { printWriter.println(500) }
        verify(exactly = 4) { response.writer }
        verify { response.contentType = "text/html" }
        verify { response.status = HttpServletResponse.SC_OK }
        confirmVerified(response)
    }

    @Test
    fun testDoGetCount() {
        mock("count", "SELECT COUNT(*) FROM PRODUCT", 1)

        QueryServlet().doGet(request, response)

        verify(exactly = 1) { printWriter.println(1) }
        verify(exactly = 4) { response.writer }
        verify { response.contentType = "text/html" }
        verify { response.status = HttpServletResponse.SC_OK }
        confirmVerified(response)
    }

    private fun mock(command: String, sqlQuery: String, intForReturnByResultSet: Int = 0) {
        val itemName = "testItem"
        val itemPrice = 500
        every { response.writer } returns printWriter
        every { response.contentType = any() } returns Unit
        every { response.status = any() } returns Unit
        every { printWriter.println(any() as Int) } returns Unit
        every { request.getParameter("command") } returns command
        every { connection.createStatement() } returns statement
        every { connection.close() } returns Unit
        every { statement.executeQuery(sqlQuery) } returns resultSet
        every { statement.close() } returns Unit
        every { printWriter.println(any() as String) } returns Unit
        every { resultSet.next() } returnsMany listOf(true, false)
        every { resultSet.getInt("price") } returns itemPrice
        every { resultSet.getString("name") } returns itemName
        every { resultSet.getInt(1) } returns intForReturnByResultSet
        every { resultSet.close() } returns Unit
    }
}