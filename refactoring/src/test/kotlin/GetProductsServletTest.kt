import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet
import java.io.PrintWriter
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GetProductsServletTest {
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
    fun testAddProductDoGet() {
        val name = "testItem"
        val price = 500

        every { response.writer } returns printWriter
        every { response.contentType = "text/html" } returns Unit
        every { response.status = HttpServletResponse.SC_OK } returns Unit
        every { printWriter.println(any() as String) } returns Unit
        every { connection.createStatement() } returns statement
        every { connection.close() } returns Unit
        every { statement.close() } returns Unit
        every { statement.executeQuery("SELECT * FROM PRODUCT") } returns resultSet
        every { resultSet.next() } returnsMany listOf(true, false)
        every { resultSet.getString("name") } returns name
        every { resultSet.getInt("price") } returns price
        every { resultSet.close() } returns Unit

        GetProductsServlet().doGet(request, response)

        verify(exactly = 1) { printWriter.println("$name\t$price</br>") }
        verify(exactly = 3) { response.writer }
        verify { response.contentType = "text/html" }
        verify { response.status = HttpServletResponse.SC_OK }
        confirmVerified(response)
    }
}