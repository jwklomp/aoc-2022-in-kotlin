fun main() {
    val lookup = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun part1(input: List<String>): Int =
        input
            .map { it.chunked(it.length / 2) }
            .map {
                it.first()
                    .split("")
                    .intersect(it.last().split("").toSet()).first { it.isNotEmpty() }
            }
            .sumOf { lookup.indexOf(it) }


    fun part2(input: List<String>): Int =
        input.filterNot { it == "" }.chunked(3)
            .map {
                it.first()
                    .split("")
                    .intersect(it[1].split("").toSet())
                    .intersect(it.last().split("").toSet()).first { it.isNotEmpty() }
            }
            .sumOf { lookup.indexOf(it) }

    val testInput = readInput("Day03_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
