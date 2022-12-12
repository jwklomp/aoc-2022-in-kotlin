fun main() {
    val lookup = "_abcdefghijklmnopqrstuvwxyz"

    val neighborFilterFn: (other: Node<Int>, self: Node<Int>) -> Boolean =
        { other, self -> other.isNeighborOf(self) && other.value <= self.value + 1 }

    fun convertToNrs(inputAsStrings: List<List<String>>): List<List<Int>> =
        inputAsStrings.map { strings ->
            strings.map {
                when (it) {
                    "S" -> 1
                    "E" -> 26
                    else -> lookup.indexOf(it)
                }
            }
        }

    fun part1(input: List<String>): Int {
        val inputAsStrings = input.map { it.chunked(1) }
        val startNodePos = findIndex(inputAsStrings, "S")
        val endNodePos = findIndex(inputAsStrings, "E")

        val grid: Grid2D<Int> = Grid2D(convertToNrs(inputAsStrings))
        val startNode = grid.getCell(startNodePos.first(), startNodePos.last())
        val endNode = grid.getCell(endNodePos.first(), endNodePos.last())

        val dijkstraShortestPath = dijkstraSP(grid.getAllCells(), startNode, endNode, neighborFilterFn)
        return dijkstraShortestPath.size - 1
    }

    fun part2(input: List<String>): Int {
        val inputAsStrings = input.map { it.chunked(1) }
        val grid: Grid2D<Int> = Grid2D(convertToNrs(inputAsStrings))

        val endNodePos = findIndex(inputAsStrings, "E")

        val minValue = grid.getAllCells().minOfOrNull { it.value }
        val startNodes = grid.getAllCells().filter { it.value == minValue }
        val endNode = grid.getCell(endNodePos.first(), endNodePos.last())

        val shortestPathsForAllStartingPositions =
            startNodes.map { startNode -> dijkstraSP(grid.getAllCells(), startNode, endNode, neighborFilterFn) }

        return shortestPathsForAllStartingPositions.minOfOrNull { it.size }?.minus(1) ?: 0
    }

    val testInput = readInput("Day12_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
