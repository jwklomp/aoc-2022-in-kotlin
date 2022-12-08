fun main() {
    fun isVisible(grid: Grid2D<Int>, cell: Cell<Int>): Boolean {
        val row = grid.getRow(cell.y) // y fixed, x variable
        val isLeftVisible = row.filter { it.x < cell.x }.all { it.value < cell.value }
        val isRightVisible = row.filter { it.x > cell.x }.all { it.value < cell.value }
        val col = grid.getCol(cell.x) // x fixed, y variable
        val isUpVisible = col.filter { it.y < cell.y }.all { it.value < cell.value }
        val isDownVisible = col.filter { it.y > cell.y }.all { it.value < cell.value }
        return isLeftVisible || isRightVisible || isUpVisible || isDownVisible
    }

    fun getScenicScore(grid: Grid2D<Int>, cell: Cell<Int>): Int {
        val row = grid.getRow(cell.y) // y fixed, x variable
        val leftVisible =
            row.filter { it.x < cell.x }.sortedByDescending { it.x }.takeWhileInclusive { it.value < cell.value }
        val rightVisible = row.filter { it.x > cell.x }.takeWhileInclusive { it.value < cell.value }

        val col = grid.getCol(cell.x) // x fixed, y variable
        val upVisible =
            col.filter { it.y < cell.y }.sortedByDescending { it.y }.takeWhileInclusive { it.value < cell.value }
        val downVisible = col.filter { it.y > cell.y }.takeWhileInclusive { it.value < cell.value }
        return leftVisible.size * rightVisible.size * upVisible.size * downVisible.size
    }

    fun makeGrid(input: List<String>): Grid2D<Int> =
        input.map { it.chunked(1).map { c -> c.toInt() } }.run { Grid2D(this) }

    fun part1(input: List<String>): Int {
        val grid: Grid2D<Int> = makeGrid(input)
        val nonEdges = grid.getNonEdges()
        val visible = nonEdges.filter { isVisible(grid, it) }
        return (grid.getAllCells().size - nonEdges.size) + visible.size
    }

    fun part2(input: List<String>): Int? =
        makeGrid(input).run { this.getNonEdges().maxOfOrNull { getScenicScore(this, it) } }

    val testInput = readInput("Day08_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
