fun main() {
    var maxYCntr = 0
    /**
     * Model of the falling sand part 2
     * 1) If it can fall straight dawn it falls down until it encounters sand or rock, if no cell or rock it stops at the bottom (maxY)
     * 2) If it can fall left dawn it falls down left until it encounters sand or rock, if no cell or rock it stops at the bottom (maxY)
     * 3) idem right down
     * 4) repeat from 1, until down left, down and down right are blocked, that means the sand is stable.
     */
    fun placeOneUnit(grid: Grid2D<Tile>, minX: Int, maxX: Int, maxY: Int): PlacementResult {

        fun placementStep(currentX: Int, currentY: Int): PlacementResult {
            val colCells = grid.getCol(currentX)
            val maxDownCell = colCells
                .filter { it.y > currentY }
                .takeWhile { !it.value.isRock && !it.value.isFilledWithSand && it.y <= maxY }
                .lastOrNull()

            if (maxDownCell == null || maxDownCell.y == -1) {
                println("full. No more sand can be placed")
                return PlacementResult(stable = false, stop = true)
            } else if (maxDownCell.y == maxY) {
                maxDownCell.value.isFilledWithSand = true
                maxYCntr++
                println("MaxY reached for $maxYCntr time. Cell $maxDownCell")
                return PlacementResult(stable = true, stop = false)
            }

            val isStableLeft = grid.getCell(maxDownCell.x - 1, maxDownCell.y + 1)
                .let { it.value.isRock || it.value.isFilledWithSand }

            val isStableRight = grid.getCell(maxDownCell.x + 1, maxDownCell.y + 1)
                .let { it.value.isRock || it.value.isFilledWithSand }

            return if (!isStableLeft) {
                placementStep(maxDownCell.x - 1, maxDownCell.y)
            } else if (!isStableRight) {
                placementStep(maxDownCell.x + 1, maxDownCell.y)
            } else {
                maxDownCell.value.isFilledWithSand = true
                PlacementResult(stable = true, stop = false)
            }
        }

        return placementStep(500, -1)
    }


    fun placeSand(
        grid: Grid2D<Tile>,
        minX: Int,
        maxX: Int,
        maxY: Int,
    ): Int {
        var counter = 0
        while (true) {
            val result = placeOneUnit(grid, minX, maxX, maxY)
            if (!result.stop) {
                counter++
            } else {
                return counter
            }
        }
    }


    fun part2(input: List<String>): Int {
        // Trajectory like 498,4 -> 498,6 -> 496,6
        val rockPaths: List<RockPath> = input.map {
            it.split(" -> ").map { i ->
                i.split(",").map { c -> c.toInt() }
            }
        }
        val flattened = rockPaths.flatMap { it }.flatMap { it }.chunked(2)

        val maxY = flattened.maxOf { it.last() } + 1

        // assumption width in both directions will not exceed height, so min and max x can be calculated.
        val minX = 500 - maxY
        val maxX = 500 + maxY

        val list2D = (0..maxY).map { (0..maxX + 1).map { Tile() } }
        var grid: Grid2D<Tile> = Grid2D(list2D)

        // fill rocks
        fillRocks(grid, rockPaths)
        printGridFn(toTotalGridFn, grid.getNrOfRows(), grid, minX, maxX)

        var units = placeSand(grid, minX, maxX, maxY)

        printGridFn(toTotalGridFn, grid.getNrOfRows(), grid, minX, maxX)
        return units
    }

    val testInput = readInput("Day14_test")
    println(part2(testInput))

    val input = readInput("Day14")
    println(part2(input))
}
