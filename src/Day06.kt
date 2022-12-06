fun main() {
    fun findForWindow(input: String, window: Int): Int? =
        input.windowed(window).find { it.toSet().size == it.length}?.let { input.indexOf(it) + window }

    fun part1(input: List<String>): Int? = findForWindow(input.first(), 4)

    fun part2(input: List<String>): Int? = findForWindow(input.first(), 14)

    val testInput = readInput("Day06_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
