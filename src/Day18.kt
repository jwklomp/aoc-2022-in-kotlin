data class XYZ(val x: Int, val y: Int, val z: Int)

fun getImmediateSurrounding(cube: XYZ): List<XYZ> {
    val rel = listOf(XYZ(-1, 0, 0), XYZ(1, 0, 0), XYZ(0, -1, 0), XYZ(0, 1, 0), XYZ(0, 0, -1), XYZ(0, 0, 1))
    return rel.map { coordinate -> XYZ(cube.x + coordinate.x, cube.y + coordinate.y, cube.z + coordinate.z) }
}

fun toCubes(input: List<String>): List<XYZ> =
    input.map { it.split(",") }.map { trio -> XYZ(trio[0].toInt(), trio[1].toInt(), trio[2].toInt()) }

const val CUBE_SIDES = 6

fun main() {
    fun part1(input: List<String>): Int {
        val cubes = toCubes(input)
        toCubes(input).sumOf { cube ->
            (CUBE_SIDES - getImmediateSurrounding(cube).filter { surroundingCube ->
                cubes.contains(
                    surroundingCube
                )
            }.size)
        }
    }

    /**
     * A cube is reachable for steam when:
     * - cube is on the edge of the 3d matrix
     * - cube has at least one ImmediateSurrounding cube that is reachable.
     */
    fun part2(input: List<String>): Int {
        val cubes = toCubes(input)
        toCubes(input).sumOf { cube ->
            (CUBE_SIDES - getImmediateSurrounding(cube).filter { surroundingCube ->
                cubes.contains(
                    surroundingCube
                )
            }.size)
        }
    }

    val testInput = readInput("Day18_test")
    //println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day18")
    //println(part1(input))
    //println(part2(input))
}
