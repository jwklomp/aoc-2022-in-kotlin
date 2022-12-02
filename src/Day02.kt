fun main() {
    val moveScoreMap1 = mapOf(
        "A X" to 3+1, "A Y" to 6+2, "A Z" to 0+3,
        "B X" to 0+1, "B Y" to 3+2, "B Z" to 6+3,
        "C X" to 6+1, "C Y" to 0+2, "C Z" to 3+3
    )

    val moveScoreMap2 = mapOf(
        "A X" to 3+0, "A Y" to 1+3, "A Z" to 2+6,
        "B X" to 1+0, "B Y" to 2+3, "B Z" to 3+6,
        "C X" to 2+0, "C Y" to 3+3, "C Z" to 1+6
    )

    fun part1(input: List<String>): Int =
        input.sumOf { moveScoreMap1.getOrDefault(it, 0) }

    fun part2(input: List<String>): Int =
        input.sumOf { moveScoreMap2.getOrDefault(it, 0) }

    val testInput = readInput("Day02_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
