data class Pos(val x: Int, val y: Int)
data class Change(val x: Int, val y: Int)
data class MoveData(val knotPositions: MutableList<Pos>, var visited: MutableList<Pos>)

val directions = listOf("L", "R", "U", "D")
val directionTranslations = listOf(Change(-1, 0), Change(1, 0), Change(0, 1), Change(0, -1))
val sp = Pos(0, 0)

fun main() {
    fun toMove(str: String): List<String> = str.split(" ").run { List(this[1].toInt()) { this[0] } }

    //  TODO rewrite to shorter using Chebyshev distance: https://iq.opengenus.org/euclidean-vs-manhattan-vs-chebyshev-distance/
    fun determineTailChangeBasedOnPosDiff(headPos: Pos, oldTailPos: Pos): Change {
        val diff = Pos(headPos.x - oldTailPos.x, headPos.y - oldTailPos.y)
        val pd: Change =
            when (diff.x) {
                -2 -> when (diff.y) {
                    -2, -1 -> Change(-1, -1)
                    0 -> Change(-1, 0)
                    1, 2 -> Change(-1, 1)
                    else -> Change(0, 0)
                }

                -1 -> when (diff.y) {
                    -2 -> Change(-1, -1)
                    2 -> Change(-1, 1)
                    else -> Change(0, 0)
                }

                0 -> when (diff.y) {
                    -2 -> Change(0, -1)
                    2 -> Change(0, 1)
                    else -> Change(0, 0)
                }

                1 -> when (diff.y) {
                    -2 -> Change(1, -1)
                    2 -> Change(1, 1)
                    else -> Change(0, 0)
                }

                2 -> when (diff.y) {
                    -2, -1 -> Change(1, -1)
                    0 -> Change(1, 0)
                    1, 2 -> Change(1, 1)
                    else -> Change(0, 0)
                }

                else -> Change(0, 0)
            }
        return pd
    }

    fun doMoveForKnots(moveData: MoveData, move: String): MoveData {
        fun doMoveForKnotPair(change: Change, i: Int): MoveData {
            val oldHeadPos = moveData.knotPositions[i]
            val oldTailPos = moveData.knotPositions[i + 1]
            val newHeadPos = if (i == 0) Pos(oldHeadPos.x + change.x, oldHeadPos.y + change.y) else oldHeadPos
            val newChange = determineTailChangeBasedOnPosDiff(newHeadPos, oldTailPos)
            val newTailPos = Pos(oldTailPos.x + newChange.x, oldTailPos.y + newChange.y)
            moveData.knotPositions[i] = newHeadPos
            moveData.knotPositions[i + 1] = newTailPos
            return if (i == moveData.knotPositions.size - 2) {
                moveData.visited.add(newTailPos)
                moveData
            } else {
                doMoveForKnotPair(newChange, i + 1)
            }
        }

        val initialChange = directionTranslations[directions.indexOf(move)]
        return doMoveForKnotPair(initialChange, 0)
    }

    fun part1(input: List<String>): Int {
        val moves = input.flatMap { toMove(it) }
        return moves.fold(MoveData(mutableListOf(sp, sp), mutableListOf(sp))) { acc: MoveData, move: String ->
            return@fold doMoveForKnots(acc, move)
        }.visited.toSet().size
    }

    fun part2(input: List<String>): Int {
        val moves = input.flatMap { toMove(it) }
        return moves.fold(
            MoveData(mutableListOf(sp, sp, sp, sp, sp, sp, sp, sp, sp, sp), mutableListOf(sp))
        ) { acc: MoveData, move: String ->
            return@fold doMoveForKnots(acc, move)
        }.visited.toSet().size
    }

    val testInput = readInput("Day09_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
