data class Tile(var isRock: Boolean = false, var isFilledWithSand: Boolean = false)

data class PlacementResult(var stop: Boolean = false, var stable: Boolean = false)

typealias RockPath = List<List<Int>>

fun fillRocks(grid: Grid2D<Tile>, rockPaths: List<RockPath>) {
    val filledPos = mutableListOf<Pair<Int, Int>>()

    rockPaths.forEach { rockPath ->
        rockPath.windowed(2).forEach { path ->
            val isHorizontal = path.first().last() == path.last().last()
            val isVertical = path.first().first() == path.last().first()
            when (isHorizontal) {
                true -> when (isVertical) {
                    true -> println("error both v and h")
                    false -> {
                        // horizontal, y is fixed x varies
                        val y = path.last().last()
                        val points = listOf(path.first().first(), path.last().first()).sorted()
                        val range = (points.first()..points.last()).toList()
                        range.forEach { filledPos.add(Pair(it, y)) }
                    }
                }

                false -> when (isVertical) {
                    true -> {
                        // vertical, x is fixed y varies
                        val x = path.last().first()
                        val points = listOf(path.first().last(), path.last().last()).sorted()
                        val range = (points.first()..points.last()).toList()
                        range.forEach { filledPos.add(Pair(x, it)) }
                    }
                    false -> println("error neither v and h")
                }
            }
        }
    }
    val filledList = filledPos.toSet()
    println("total nr of rocks: ${filledList.size}")
    filledList.forEach {
        val cell = grid.getCell(it.first, it.second)
        cell.value.isRock = true
    }
}

val toTotalGridFn: (Cell<Tile>) -> String = {
    it
    if (it.x == 500 && it.y == 0) {
        "+"
    } else if (it.value.isRock) {
        "#"
    } else if (it.value.isFilledWithSand) {
        "O"
    } else {
        "."
    }
}


fun printGridFn(printFn: (Cell<Tile>) -> String, nrOfRows: Int, grid: Grid2D<Tile>, minX: Int, maxX: Int) {
    (0 until nrOfRows).toList().forEach { rowNr ->
        grid.getRow(rowNr).filter { it.x in minX..maxX }.joinToString("") { printFn(it) }.let { println(it) }
    }
}

fun main() {

    /**
     * Model of the falling sand
     * 1) If it can fall straight dawn it falls down until it encounters sand or rock, if no cell or rock it falls down forever and process stops, else
     * 2) If it can fall left dawn it falls down left until it encounters sand or rock, if no cell or rock it falls down forever and process stops, else
     * 3) idem right down
     * 4) repeat from 1, until down left, down and down right are blocked, that means the sand is stable.
     */
    fun placeOneUnit(grid: Grid2D<Tile>, minX: Int, maxX: Int, maxY: Int): PlacementResult {

        fun placementStep(currentX: Int, currentY: Int): PlacementResult {
            val colCells = grid.getCol(currentX)
            val maxDownCell = colCells.filter { it.y > currentY  }.takeWhile {!it.value.isRock && !it.value.isFilledWithSand }.lastOrNull()
            if (maxDownCell != null) {
                if(maxDownCell.y == maxY && !maxDownCell.value.isRock) {
                    println("falling into the void - bottom")
                    return PlacementResult(stop = true)
                }

                val isStableLeft = grid.getCell(maxDownCell.x-1, maxDownCell.y+1).let { it.value.isRock || it.value.isFilledWithSand }

                val isStableRight = grid.getCell(maxDownCell.x+1, maxDownCell.y+1).let { it.value.isRock || it.value.isFilledWithSand }

                return if (!isStableLeft) {
                    if ((maxDownCell.x - 1) <= minX) {
                        println("falling into the void - left")
                        PlacementResult(stop = true)
                    } else {
                        placementStep(maxDownCell.x - 1, maxDownCell.y)
                    }
                } else if (!isStableRight) {
                    if ((maxDownCell.x + 1) >= maxX) {
                        println("falling into the void - right")
                        PlacementResult(stop = true)
                    } else {
                        placementStep(maxDownCell.x + 1, maxDownCell.y)
                    }
                } else {
                    maxDownCell.value.isFilledWithSand = true
                    PlacementResult(stable = true)
                }
            } else {
                error("No max found for $currentX $currentY")
            }
        }

        return placementStep(500, 0)
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
            if (result.stable) {
                counter++
            } else {
                return counter
            }
        }
    }


    fun part1(input: List<String>): Int {
        // Trajectory like 498,4 -> 498,6 -> 496,6
        val rockPaths: List<RockPath> = input.map {
            it.split(" -> ").map { i ->
                i.split(",").map { c -> c.toInt() }
            }
        }
        val flattened = rockPaths.flatMap { it }.flatMap { it }.chunked(2)
        val minX = flattened.minOf { it.first() }
        val maxX = flattened.maxOf { it.first() }
        println(maxX)
        val maxY = flattened.maxOf { it.last() }
        println(maxY)

        val list2D = (0..maxY).map { (0..maxX + 1).map { Tile() } } // add one to max to avoid index out of bound
        var grid: Grid2D<Tile> = Grid2D(list2D)

        // fill rocks
        fillRocks(grid, rockPaths)
        printGridFn(toTotalGridFn, grid.getNrOfRows(), grid, minX, maxX)

        var units = placeSand(grid, minX, maxX, maxY)

        printGridFn(toTotalGridFn, grid.getNrOfRows(), grid, minX, maxX)
        return units
    }

    fun part2(input: List<String>): Int {
        val result = input.map { it.split(",") }


        return 1
    }

    val testInput = readInput("Day14_test")
    println(part1(testInput))
    //println(part2(testInput))

    val input = readInput("Day14")
    println(part1(input))
    //println(part2(input))
}
