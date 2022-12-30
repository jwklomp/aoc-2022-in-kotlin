import kotlin.math.abs

data class Reading(val sensor: Point, val beacon: Point)

fun makeReadings(input: List<String>): List<Reading> {
    val pointList = input.map {
        it.replace("Sensor at ", "").replace(" closest beacon is at ", "").replace(":", ", ").split(", ")
    }
    return pointList.map {
        Reading(
            sensor = Point(it[0].split("=")[1].toInt(), it[1].split("=")[1].toInt()),
            beacon = Point(it[2].split("=")[1].toInt(), it[3].split("=")[1].toInt())
        )
    }
}

fun main() {
    fun part1(input: List<String>): Int {

        //val targetY = 10
        val targetY = 2000000
        val readings = makeReadings(input)
        println(readings)
        // get min and max X and Y
        val allX = readings.flatMap { listOf(it.sensor.x, it.beacon.x) }
        val minX = allX.min()
        val maxX = allX.max()
        val allSensorsXOnTargetY = readings.filter { it.sensor.y == targetY  }.map { it.sensor.x }
        val allBeaconXOnTargetY = readings.filter { it.beacon.y == targetY  }.map { it.beacon.x }
        val allXOnTargetY = allBeaconXOnTargetY.union(allSensorsXOnTargetY)
        println("allXOnTargetY")
        println(allXOnTargetY)

        // map over calculate the spots taken for y = ...
        val ranges = readings.mapNotNull {
            val mdBeaconSensor = manhattanDistance(it.sensor, it.beacon)
            val distance = abs(targetY - it.sensor.y) - mdBeaconSensor // negative when useful
            if (distance <= 0) {
                val min = it.sensor.x + distance
                val max = it.sensor.x - distance
                return@mapNotNull IntRange(min, max)
            } else {
                return@mapNotNull null
            }
        }
        println("ranges")
        println(ranges)

        val blockedRange = ranges.fold(emptySet<Int>()){ sum, element -> sum.union(element)}.minus(allXOnTargetY)

        val noBeaconRange = IntRange(minX, maxX).intersect(blockedRange)
        return noBeaconRange.size
    }

    val testInput = readInput("Day15_test")
    println(part1(testInput))

    val input = readInput("Day15")
    println(part1(input))
}
