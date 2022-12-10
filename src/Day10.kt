import kotlin.math.abs

data class SignalAcc(var registerX: Long, var sumSignal: Long)
data class PixelAcc(var registerX: Long, var pixels: MutableList<String>)

fun main() {
    fun toCycles(input: List<String>) = input.map { it.split(" ") }
        .flatMap { if (it.first() == "addx") listOf(0, it.last().toInt()) else listOf(0) }

    fun part1(input: List<String>): Long {
        val signalAcc = toCycles(input).foldIndexed(SignalAcc(1, 0)) { idx, signalAcc, cycleValue ->
            val cycle = idx + 1
            if (listOf(20, 60, 100, 140, 180, 220).contains(idx + 1)) signalAcc.sumSignal += signalAcc.registerX * cycle
            signalAcc.registerX += cycleValue
            return@foldIndexed signalAcc
        }
        return signalAcc.sumSignal
    }

    fun part2(input: List<String>): List<List<String>> {
        val initialPixels = ".".repeat(40 * 6).chunked(1) as MutableList<String>
        val pixelAcc = toCycles(input).foldIndexed(PixelAcc(1, initialPixels)) { idx, pixelAcc, cycleValue ->
            if (abs(pixelAcc.registerX - (idx % 40)) <= 1) pixelAcc.pixels[idx] = "#"
            pixelAcc.registerX += cycleValue
            return@foldIndexed pixelAcc
        }
        return pixelAcc.pixels.chunked(40)
    }

    val testInput = readInput("Day10_test")
    println(part1(testInput))
    part2(testInput).forEach { println(it.joinToString("")) }

    val input = readInput("Day10")
    println(part1(input))
    part2(input).forEach { println(it.joinToString("")) }
}
