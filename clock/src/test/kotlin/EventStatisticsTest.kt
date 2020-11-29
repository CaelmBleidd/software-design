import clock.SettableClock
import event.ClockEventStatistics
import event.EventStatistics
import java.time.Instant
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlin.math.abs
import org.junit.Before
import org.junit.Test

class EventStatisticsTest {
    private lateinit var clock: SettableClock
    private lateinit var statsManager: EventStatistics

    @Before
    fun initialize() {
        clock = SettableClock(Instant.ofEpochSecond(0L))
        statsManager = ClockEventStatistics(clock)
    }

    @Test
    fun emptyStat() {
        val eventName = "event"
        assertTrue(equals(statsManager.getEventStatisticByName(eventName), 0.0))
        assertEquals(0, statsManager.getAllEventStatistic().size)
    }

    @Test
    fun singleRecord() {
        val events = listOf("firstEvent", "secondEvent")
        statsManager.incEvent(events.first())
        assertTrue(equals(statsManager.getEventStatisticByName(events.last()), 0.0))
        assertEquals(1, statsManager.getAllEventStatistic().size)
    }

    @Test
    fun multipleRecords() {
        val eventName = "event"
        val count = 500
        for (i in 1 until count) {
            statsManager.incEvent(eventName)
            assertTrue(equals(statsManager.getEventStatisticByName(eventName), i / 60.0))
            assertEquals(1, statsManager.getAllEventStatistic().size)
        }
    }

    @Test
    fun multipleRecordsWithChangingTime() {
        val eventName = "event"
        assertTrue(equals(statsManager.getEventStatisticByName(eventName), 0.0))
        assertEquals(0, statsManager.getAllEventStatistic().size)
        clock.now = Instant.ofEpochSecond(123)
        assertTrue(equals(statsManager.getEventStatisticByName(eventName), 0.0))
        assertEquals(0, statsManager.getAllEventStatistic().size)
        clock.now = Instant.ofEpochSecond(3800)
        assertTrue(equals(statsManager.getEventStatisticByName(eventName), 0.0))
        assertEquals(0, statsManager.getAllEventStatistic().size)
    }

    @Test
    fun oneHourPassed() {
        val eventName = "event"
        statsManager.incEvent(eventName)
        assertTrue(equals(statsManager.getEventStatisticByName(eventName), 1.0 / 60.0))
        assertEquals(1, statsManager.getAllEventStatistic().size)
        clock.now = Instant.ofEpochSecond(123)
        assertTrue(equals(statsManager.getEventStatisticByName(eventName), 1.0 / 60.0))
        assertEquals(1, statsManager.getAllEventStatistic().size)
        clock.now = Instant.ofEpochSecond(3600)
        assertTrue(equals(statsManager.getEventStatisticByName(eventName), 1.0 / 60.0))
        assertEquals(1, statsManager.getAllEventStatistic().size)
        clock.now = Instant.ofEpochSecond(3601)
        assertTrue(equals(statsManager.getEventStatisticByName(eventName), 0.0))
        assertEquals(1, statsManager.getAllEventStatistic().size)
    }

    @Test
    fun multiTest() {
        val events = listOf("event0", "event1", "event2", "event3", "event4", "event5")
        val periods = listOf(4, 8, 15, 16, 23, 42)
        val count = 500
        for (i in 1..count) {
            for (j in events.indices) {
                val eventName = events[j]
                val period = periods[j]
                clock.now = Instant.ofEpochSecond(i * period.toLong())
                statsManager.incEvent(eventName)
            }
        }
        assertEquals(events.size, statsManager.getAllEventStatistic().size)
        clock.now = Instant.ofEpochSecond(0L)
        for (eventName in events) {
            assertTrue(equals(statsManager.getEventStatisticByName(eventName), 0.0))
        }
        val max = count * periods[periods.size - 1]
        for (i in 0 until max) {
            clock.now = Instant.ofEpochSecond(i.toLong())
            for (j in events.indices) {
                val eventName = events[j]
                val period = periods[j]
                if (i < count * period) {
                    var first = period * ((i - 3600) / period)
                    if ((i - 3600) % period != 0) {
                        first += period
                    }
                    val start = period.coerceAtLeast(first)
                    val cnt = (i - start + period).coerceAtLeast(0) / period
                    assertTrue(equals(statsManager.getEventStatisticByName(eventName), cnt / 60.0))
                }
            }
        }
    }

    companion object {
        private const val EPSILON = 1e-10;

        private fun equals(lhs: Double, rhs: Double): Boolean {
            return abs(lhs - rhs) <= EPSILON;
        }
    }
}