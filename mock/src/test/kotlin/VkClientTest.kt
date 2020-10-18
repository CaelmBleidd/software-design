import org.junit.Assert
import org.junit.Test
import java.time.Instant
import java.util.concurrent.TimeUnit


class VkClientTest {
    private val host = "api.vk.com"

    @Test
    fun getInfoTest() {
        val client = VkClient(host)
        val n = 10
        val infos = client.getInfo("Россия", n)
        val endTime = Instant.now().epochSecond
        val startTime = endTime - n * TimeUnit.HOURS.toSeconds(n.toLong())
        println(infos.joinToString("\n"))
        for (info in infos) {
            Assert.assertTrue(info.date in startTime..endTime)
        }
    }
}