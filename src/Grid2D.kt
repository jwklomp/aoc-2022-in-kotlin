class Grid2D<T>(private val grid: List<List<T>>) {
    private val rowLength: Int = grid.size
    private val columnLength: Int = grid.first().size

    private val surrounding: List<Pair<Int, Int>> = listOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, -1), Pair(0, 1), Pair(1, -1), Pair(1, 0), Pair(1, 1))
    private val adjacent: List<Pair<Int, Int>> = listOf(Pair(-1, 0), Pair(0, -1), Pair(0, 1), Pair(1, 0))

    fun getCell(x: Int, y: Int): Cell<T> = Cell(value = grid[y][x], x = x, y = y)

    fun getAllCells(): List<Cell<T>> = grid.flatMapIndexed { y, row -> row.mapIndexed { x, v -> Cell(value = v, x = x, y = y) } }

    fun getCellsFiltered(filterFn: (Cell<T>) -> (Boolean)): List<Cell<T>> = getAllCells().filter { filterFn(it) }

    fun getSurrounding(x: Int, y: Int): List<Cell<T>> = filterPositions(surrounding, x, y)

    fun getAdjacent(x: Int, y: Int): List<Cell<T>> = filterPositions(adjacent, x, y)

    private fun filterPositions(positions: List<Pair<Int, Int>>, x: Int, y: Int): List<Cell<T>> =
        positions
            .map { Pair(it.first + x, it.second + y) }
            .filter { it.first >= 0 && it.second >= 0 }
            .filter { it.first < rowLength && it.second < columnLength }
            .map { getCell(it.first, it.second) }
}

data class Cell<T>(val value: T, val x: Int, val y: Int)
