fun main() {

    fun getSums(input: List<String>): List<Int> {
        val indexes = listOf(0) + input.indexesOf("") + listOf(input.size)
        return indexes.windowed(2)
            .map { input.subList(it.first(), it.last()).filterNot { it == "" }.map { it.toInt() }.sum() }
    }

    fun part1(input: List<String>): Int =
        getSums(input).max()

    fun part2(input: List<String>): Int =
        getSums(input).sortedDescending().take(3).sum()

    val testInput = readInput("Day01_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
