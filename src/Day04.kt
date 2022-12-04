fun main() {
    fun isFullOverlap(a: List<String>, b: List<String>): Boolean =
        (a.first().toInt() <= b.first().toInt() && a.last().toInt() >= b.last().toInt()) || (b.first()
            .toInt() <= a.first().toInt() && b.last().toInt() >= a.last().toInt())

    fun isPartialOverlap(a: List<String>, b: List<String>): Boolean =
        (a.first().toInt()..a.last().toInt()).intersect(b.first().toInt()..b.last().toInt()).isNotEmpty()

    fun part1(input: List<String>): Int =
        input
            .map { it.split(",") }
            .map { i -> i.map { it.split("-") } }
            .count { isFullOverlap(it.first(), it.last()) }

    fun part2(input: List<String>): Int =
        input
            .map { it.split(",") }
            .map { i -> i.map { it.split("-") } }
            .count { isPartialOverlap(it.first(), it.last()) }

    val testInput = readInput("Day04_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
