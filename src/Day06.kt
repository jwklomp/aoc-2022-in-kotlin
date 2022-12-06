fun main() {
    fun findForWindow(input: List<String>, window: Int): Int {
        val windowed = input.first()
            .split("")
            .windowed(window)
            .filter { !it.contains("")}

        val firstUnique = windowed.find { strings ->  strings.distinct().size == window}
        return windowed.indexOfFirst { it == firstUnique } + window
    }

    fun part1(input: List<String>): Int = findForWindow(input, 4)

    fun part2(input: List<String>): Int = findForWindow(input, 14)

    val testInput = readInput("Day06_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
