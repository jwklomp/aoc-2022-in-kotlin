data class FileProp(val path: String, val name: String, val size: Long)
data class Accumulator(
    var currentPath: String,
    val allDirs: MutableList<String>,
    val files: MutableList<FileProp>
)

fun main() {
    fun accumulator(input: List<String>): Accumulator {
        val initial =
            Accumulator(currentPath = "root", allDirs = mutableListOf("root"), files = mutableListOf())
        val dirStructure: Accumulator = input.fold(initial) { acc: Accumulator, it: String ->
            val props = it.split(" ")
            if (props[0].toLongOrNull() != null) {
                val newElement = FileProp(path = acc.currentPath, name = props[1], size = props[0].toLong())
                acc.files.add(newElement)
            } else if (it == "\$ cd ..") {
                val lastIndex = acc.currentPath.lastIndexOf("|")
                acc.currentPath = acc.currentPath.substring(0, lastIndex)
            } else if (it.contains("\$ cd") && props[2] != "/") {
                acc.currentPath += "|${props[2]}"
                acc.allDirs.add(acc.currentPath)
            }
            return@fold acc
        }
        return dirStructure
    }

    fun determineDirSizes(input: List<String>): List<Long> {
        val dirStructure: Accumulator = accumulator(input)
        return dirStructure.allDirs.toSet().map { dir ->
            dirStructure.files.filter { file -> file.path.startsWith(dir) }.sumOf { file -> file.size }
        }
    }

    fun part1(input: List<String>): Long =
        determineDirSizes(input).filter { it <= 100000 }.sum()

    fun part2(input: List<String>): Long {
        val dirSizes = determineDirSizes(input)
        val spaceToFree = dirSizes.max() - 40000000
        return dirSizes.filter { it >= spaceToFree }.min()
    }

    val testInput = readInput("Day07_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
