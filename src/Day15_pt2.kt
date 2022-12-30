import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


fun main() {
    /**
     * Solution of part one with ranges is way too slow, would take in the order of 90 days, so need to start from scratch
     * Steps:
     * - for each of the lines between 0 and 4000000 (= y position):
     *   - for each sensor-beacon combinations, calculate the interval on the line between 0 and 4000000 (x-from and x-t0), if any
     *   - sort the intervals and merge them using the algorithm described here:
     *     see https://scicomp.stackexchange.com/questions/26258/the-easiest-way-to-find-intersection-of-two-intervals
     * - In the result there should be 1 line, where the merged result is not 1 interval but 2 intervals with a gap of 1
     *   This gap is the distress signal position. Frequency = x-pos * 4000000 + y-pos
     */
    fun part2(input: List<String>) {
        val maxCoordinate = if (input.size == 14) 20 else 4000000
        val readings = makeReadings(input)

        (0..maxCoordinate).toList()
            .forEach { y ->
                val intervals = readings.mapNotNull { reading ->
                    val mdBeaconSensor = manhattanDistance(reading.sensor, reading.beacon)
                    val distance = abs(y - reading.sensor.y) - mdBeaconSensor // negative when useful
                    if (distance <= 0) return@mapNotNull Interval(
                        from = reading.sensor.x + distance,
                        to = reading.sensor.x - distance
                    )
                    else return@mapNotNull null
                }

                val mergedIntervals = mergeIntervals(intervals)

                val intervalsInRange = mergedIntervals
                    .filter { interval -> interval.from <= maxCoordinate && interval.to >= 0 }
                    .map { interval -> Interval(from = max(interval.from, 0), to = min(interval.to, maxCoordinate)) }

                if (intervalsInRange.size > 1) {
                    val x = intervalsInRange[0].to + 1
                    val frequency: Long = (x * 4000000L) + y
                    println("frequency: $frequency") // 12525726647448
                }
            }
    }

    //val testInput = readInput("Day15_test")
    //part2(testInput)

    val input = readInput("Day15")
    part2(input)
}
