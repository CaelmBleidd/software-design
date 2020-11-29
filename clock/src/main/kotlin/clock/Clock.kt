package clock

import java.time.Instant

interface Clock {
    fun now(): Instant
}

class SettableClock(var now: Instant) : Clock {
    override fun now() = now
}