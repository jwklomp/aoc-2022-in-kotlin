data class XY(var x: Int, var y: Int)

data class Rock(
    val name: String,
    val shape: List<XY>,
    val neededFreeDown: List<XY>,
    val neededFreeLeft: List<XY>,
    val neededFreeRight: List<XY>,
    var currentPosition: XY, // bottom left position
    val xMax: Int,
    val height: Int,
)

data class JetPattern(
    val masterPattern: List<String>,
    var currentPattern: MutableList<String>
) {
    fun getNext(): String {
        if (currentPattern.size == 0) {
            currentPattern.addAll(masterPattern)
        }
        return currentPattern.removeFirst()
    }
}

typealias Grid = MutableList<MutableList<Boolean>>

/**
 * ####
 */
val horizontalLine = Rock(
    name = "horizontal-line",
    shape = listOf(XY(0, 0), XY(1, 0), XY(2, 0), XY(3, 0)),
    neededFreeDown = listOf(XY(0, -1), XY(1, -1), XY(2, -1), XY(3, -1)),
    neededFreeLeft = listOf(XY(-1, 0)),
    neededFreeRight = listOf(XY(4, 0)),
    currentPosition = XY(-1, -1),
    xMax = 3,
    height = 1,
)

/**
 * .#.
 * ###
 * .#.
 */
val plus = Rock(
    name = "plus",
    shape = listOf(XY(1, 0), XY(0, 1), XY(1, 1), XY(2, 1), XY(1, 2)),
    neededFreeDown = listOf(XY(0, 0), XY(1, -1), XY(2, 0)),
    neededFreeLeft = listOf(XY(0, 0), XY(-1, 1), XY(0, 2)),
    neededFreeRight = listOf(XY(2, 0), XY(3, 1), XY(2, 2)),
    currentPosition = XY(-1, -1),
    xMax = 4,
    height = 3
)

/**
 * ..#
 * ..#
 * ###
 */
val reverseL = Rock(
    name = "reverse-l",
    shape = listOf(XY(0, 0), XY(1, 0), XY(2, 0), XY(2, 1), XY(2, 2)),
    neededFreeDown = listOf(XY(0, -1), XY(1, -1), XY(2, -1)),
    neededFreeLeft = listOf(XY(-1, 0), XY(1, 1), XY(1, 2)),
    neededFreeRight = listOf(XY(3, 0), XY(3, 1), XY(3, 2)),
    currentPosition = XY(-1, -1),
    xMax = 4,
    height = 3,
)

/**
 * #
 * #
 * #
 * #
 */
val verticalLine = Rock(
    name = "vertical-line",
    shape = listOf(XY(0, 0), XY(0, 1), XY(0, 2), XY(0, 3)),
    neededFreeDown = listOf(XY(0, -1)),
    neededFreeLeft = listOf(XY(-1, 0), XY(-1, 1), XY(-1, 2), XY(-1, 3)),
    neededFreeRight = listOf(XY(1, 0), XY(1, 1), XY(1, 2), XY(1, 3)),
    currentPosition = XY(-1, -1),
    xMax = 6,
    height = 4
)

/**
 * ##
 * ##
 */
val square = Rock(
    name = "square",
    shape = listOf(XY(0, 0), XY(0, 1), XY(1, 0), XY(1, 1)),
    neededFreeDown = listOf(XY(0, -1), XY(1, -1)),
    neededFreeLeft = listOf(XY(-1, 0), XY(-1, 1)),
    neededFreeRight = listOf(XY(2, 0), XY(2, 1)),
    currentPosition = XY(-1, -1),
    xMax = 5,
    height = 2,
)

val blocks = listOf(horizontalLine.copy(), plus.copy(), reverseL.copy(), verticalLine.copy(), square.copy())
val rockSequence = (1..405).toList().fold(listOf<Rock>()) { acc, _ -> acc + blocks }.take(2022)

const val MIN_BLANK_LINES = 3

fun getPositions(positions: List<XY>, current: XY): List<XY> =
    positions.map { XY(it.x + current.x, it.y + current.y) }

fun areAllFree(grid: Grid, positions: List<XY>) = positions.all { pos -> !grid[pos.y][pos.x] }

fun placeNewRockOnGrid(jetPattern: JetPattern, grid: Grid, rock: Rock) {
    // alternate being pushed by a jet of hot gas one unit and then falling one unit down
    fun simulateFall() {
        val pushChar = jetPattern.getNext()
        if (pushChar == "<") {
            if (rock.currentPosition.x > 0) {
                val neededFreeLeft = getPositions(rock.neededFreeLeft, rock.currentPosition)
                val canMoveLeft = areAllFree(grid, neededFreeLeft)
                if(canMoveLeft) {
                    rock.currentPosition.x -= 1
                    //println("Jet of gas pushes rock left")
                } else {
                    //println("Jet of gas pushes rock left, but nothing happens because of other block")
                }
            } else {
                //println("Jet of gas pushes rock left, but nothing happens because of wall")
            }
        } else if (pushChar == ">") {
            if (rock.currentPosition.x < rock.xMax) {
                val neededFreeRight = getPositions(rock.neededFreeRight, rock.currentPosition)
                val canMoveRight = areAllFree(grid, neededFreeRight)
                if(canMoveRight) {
                    rock.currentPosition.x += 1
                    //println("Jet of gas pushes rock right")
                } else {
                    //println("Jet of gas pushes rock right, but nothing happens because of other block")
                }
            } else {
                //println("Jet of gas pushes rock right, but nothing happens because of wall")
            }
        }
        //printGrid(grid, rock)

        val neededFreeDown = getPositions(rock.neededFreeDown, rock.currentPosition)
        val canMoveDown = neededFreeDown.all { it.y >= 0 } && areAllFree(grid, neededFreeDown)
        if (canMoveDown) {
            rock.currentPosition.y -= 1
            //println("Rock falls 1 unit")
            //printGrid(grid, rock)
            simulateFall()
        } else {
            //println("Rock falls 1 unit, causing it to come to rest")
            rock.shape.forEach { relativePosition ->
                grid[rock.currentPosition.y + relativePosition.y][rock.currentPosition.x + relativePosition.x] = true
            }
            //printGrid(grid, rock)
        }
    }
    grid.removeAll{ row -> row.all { !it } }
    val oldHeight = grid.size
    repeat(MIN_BLANK_LINES + rock.height) { grid.add(mutableListOf(false, false, false, false, false, false, false)) }
    // rock left edge is two units away from left wall and bottom edge is three units above highest rock in the room
    rock.currentPosition = XY(2, oldHeight + MIN_BLANK_LINES)
    //println("A new rock begins falling. Type: ${rock.name}")
    //printGrid(grid, rock)
    simulateFall()
}

fun printGrid(grid: Grid, rock: Rock) {
    val currentRockPositions = rock.shape.map { relativePosition ->
        XY(rock.currentPosition.x + relativePosition.x, rock.currentPosition.y + relativePosition.y)
    }
    val printGrid = grid.mapIndexed { y, row ->
        row.mapIndexed { x, bool ->
            if (currentRockPositions.contains(XY(x, y))) {
                "@"
            } else {
                if (bool) "#" else "."
            }
        }
    }
    printGrid.reversed().forEach { row ->
        println("|${row.joinToString("")}|")
    }
    println("+-------+")
    println(" ")
}

fun main() {

    fun testPrintGrid() {
        val grid = mutableListOf<MutableList<Boolean>>()
        grid.add(mutableListOf(false, true, true, true, true, false, false))
        grid.add(mutableListOf(false, false, false, false, false, false, false))
        grid.add(mutableListOf(false, false, false, false, false, false, false))
        grid.add(mutableListOf(false, false, false, false, false, false, false))
        grid.add(mutableListOf(false, false, false, false, false, false, false))
        val rock = plus.copy()
        rock.currentPosition = XY(3, 2)
        printGrid(grid, rock)
        rock.currentPosition = XY(2, 2)
        printGrid(grid, rock)
        rock.currentPosition = XY(2, 1)
        printGrid(grid, rock)
    }
    //testPrintGrid();

    fun part1(input: List<String>): Int {
        val grid = mutableListOf<MutableList<Boolean>>()
        println("input length: ${input[0].length}")
        val jet = input[0].chunked(1)
        val jetPattern: JetPattern = JetPattern(jet, jet.toMutableList())
        rockSequence.forEach { rock ->
            placeNewRockOnGrid(jetPattern, grid, rock)
        }

        grid.removeAll{ row -> row.all { !it } }
        return grid.size
    }

    fun part2(input: List<String>): Int {
        val result = input.map { it.split(",") }
        println(result)
        return 1
    }

    val testInput = readInput("Day17_test")
    //println(part1(testInput))
    //println(part2(testInput))

    val input = readInput("Day17")
    println(part1(input))
    //println(part2(input))
}
