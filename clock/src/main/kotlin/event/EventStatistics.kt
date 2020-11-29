package event

import clock.Clock
import java.lang.Long.max
import java.util.concurrent.TimeUnit

interface EventStatistics {
    fun incEvent(name: String)

    fun getEventStatisticByName(name: String): Double

    fun getAllEventStatistic(): Map<String, Double>

    fun printStatistic()
}

class ClockEventStatistics(private val clock: Clock) : EventStatistics {
    private val events = mutableMapOf<String, MutableList<Long>>()

    override fun incEvent(name: String) {
        events
            .getOrPut(name) { mutableListOf() }
            .apply { add(clock.now().epochSecond) }
    }

    override fun getEventStatisticByName(name: String): Double {
        val timings = events[name] ?: return 0.0
        val now = clock.now().epochSecond
        val hourAgo = max(0, now - TimeUnit.HOURS.toSeconds(1L))
        val eventsCountInLastHour = timings.filter { it in hourAgo..now }.size
        return eventsCountInLastHour.toDouble() / TimeUnit.HOURS.toMinutes(1L)
    }

    override fun getAllEventStatistic() = events.keys.associateWith { getEventStatisticByName(it) }

    override fun printStatistic() {
        print(
            events.map { (key, value) ->
                "Event: $key, rpm: ${value.size.toDouble() / TimeUnit.HOURS.toMinutes(1L)}"
            }.joinToString(separator = System.lineSeparator())
        )
    }
}