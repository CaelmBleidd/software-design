import java.io.File
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.concurrent.TimeUnit

open class VkClient(open val host: String) {
    open fun getInfo(hashTag: String, hours: Int): List<VkInfo> {
        val response = createUrl(hashTag, hours).readAsTest()
        return response.parseResponse()
    }

    private fun getAccessToken(): String {
        val tokenLine = File(PROPERTIES_FILE_NAME)
            .bufferedReader()
            .lines()
            .filter { it.startsWith("accessToken") }
            .findFirst()
            .orElseThrow()
        return tokenLine.split("=").last()
    }

    private fun createUrl(hashTag: String, hours: Int): String {
        val encodedHashTag = URLEncoder.encode(
            if (hashTag.startsWith("#")) hashTag else "#$hashTag",
            StandardCharsets.UTF_8.name()
        )
        val startTime = Instant.now().epochSecond - hours * TimeUnit.HOURS.toSeconds(hours.toLong())
        return "https://$host/method/newsfeed.search?" +
                "start_time=$startTime" +
                "&count=200&q=$encodedHashTag" +
                "&access_token=${getAccessToken()}&v=5.102"
    }

    companion object {
        private const val PROPERTIES_FILE_NAME = "vk.properties"
    }
}