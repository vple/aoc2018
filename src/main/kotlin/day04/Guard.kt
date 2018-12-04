package day04

/**
 * A guard.
 */
data class Guard(val id: Int, val sleepDistribution: Map<Int, Int>) {
    /** The total number of minutes that this guard has been asleep. */
    val totalTimeAsleep: Int by lazy {
        sleepDistribution.values.sum()
    }

    /** The minute and frequency when this guard is asleep the most. */
    val mostAsleepAt: Pair<Int, Int> by lazy {
        sleepDistribution.maxBy { it.value }!!.toPair()
    }

    companion object {
        const val ASLEEP = "falls asleep"
        const val AWAKE = "wakes up"
        const val TIME_PATTERN = "\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)\\]"
        val TIME_REGEX = Regex(TIME_PATTERN)

        /**
         * Parses [sortedRecords] of when guard [id] falls asleep and wakes up to create a Guard.
         */
        fun parseRecords(id: Int, sortedRecords: List<String>): Guard {
            val minutesAsleep = mutableListOf<Int>()

            var lastTime = 0
            for (record in sortedRecords) {
                val groupValues = checkNotNull(TIME_REGEX.find(record)?.groupValues)
                val minute = groupValues[5].toInt()

                // We assume that a guard always alternates between falling asleep and waking up.
                // We are told that guards always fall asleep and wake up between [00:00, 00:59].
                if (record.contains(ASLEEP)) {
                    lastTime = minute
                } else if (record.contains(AWAKE)) {
                    minutesAsleep.addAll(lastTime until minute)
                }
            }

            val timeAsleep = minutesAsleep.groupingBy { it }.eachCount()
            return Guard(id, timeAsleep)
        }
    }
}