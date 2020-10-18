import java.time.Instant

class VkManager(private val vkClient: VkClient) {
    fun getPostsCountPerHours(hashTag: String, hours: Int): List<Int> {
        if (hours !in 1..24) error("Hours must be in 1..24, actual $hours")

        val result = vkClient
            .getInfo(hashTag, hours)
            .groupingBy { it.date.getHours() }
            .eachCount()
            .toMutableMap()

        val startTime = Instant.now().epochSecond
        for (i in 0 until hours) {
            result.putIfAbsent(startTime.getHours() - i, 0)
        }
        return result.toList().sortedBy { it.first }.map { it.second }
    }

    private fun Long.getHours() = (this / SECONDS_PER_HOUR).toInt()

    companion object {
        private const val SECONDS_PER_HOUR = 60 * 60
    }
}