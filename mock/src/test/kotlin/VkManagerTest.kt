import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.time.Instant

class VkManagerTest {
    private val vkClient: VkClient = mock()
    private lateinit var vkManager: VkManager

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        vkManager = VkManager(vkClient)
    }

    @Test
    fun getPostsCountPerHoursTest() {
        val hashtag = "котик"
        val n = 4
        whenever(vkClient.getInfo(hashtag, n)).thenReturn(createAnswer())
        val counts = vkManager.getPostsCountPerHours(hashtag, n)
        Assert.assertEquals(listOf(0, 0, 2, 1), counts)
    }

    private fun createAnswer(): List<VkInfo> {
        val firstHour = Instant.now().epochSecond
        val secondHour = Instant.now().epochSecond - SECONDS_PER_HOUR
        var yetAnotherHour = secondHour + 1
        if (yetAnotherHour / SECONDS_PER_HOUR > secondHour / SECONDS_PER_HOUR) {
            yetAnotherHour -= 2
        }
        return listOf(
            VkInfo(1, 1, firstHour),
            VkInfo(2, 1, yetAnotherHour),
            VkInfo(2, 3, secondHour)
        )
    }

    companion object {
        private const val SECONDS_PER_HOUR = 60 * 60
    }
}