package com.decagonhq.clads.data.repository

import android.os.SystemClock
import android.util.ArrayMap
import java.util.concurrent.TimeUnit

class RateLimiter<in KEY>(timeOut: Int, timeUnit: TimeUnit) {
    private val timeStamps = ArrayMap<KEY, Long>()
    private val timeOut = timeUnit.toMillis(timeOut.toLong())

    @Synchronized
    fun shouldFetch(key: KEY?): Boolean {
        val lastFetched = timeStamps[key]
        val now = now()
        if (lastFetched == null) {
            timeStamps[key] = now
            return true
        }
        if (now - lastFetched > timeOut) {
            timeStamps[key] = now
            return true
        }
        return false
    }

    private fun now() = SystemClock.uptimeMillis()

    @Synchronized
    fun reset(key: KEY) {
        timeStamps.remove(key)
    }
}
