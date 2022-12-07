data class FileProp(val path: String, val name: String, val size: Long)
data class Acc(var currentPath: String, val allDirs: MutableList<String>, val files: MutableList<FileProp>)

fun main() {
    fun foldFn(input: List<String>): Acc =
        input.fold(Acc("root", mutableListOf("root"), mutableListOf())) { acc: Acc, it: String ->
            val props = it.split(" ")
            if (props[0].toLongOrNull() != null) {
                val newElement = FileProp(path = acc.currentPath, name = props[1], size = props[0].toLong())
                acc.files.add(newElement)
            } else if (it == "\$ cd ..") {
                acc.currentPath = acc.currentPath.substringBeforeLast("|")
            } else if (it.contains("\$ cd") && props[2] != "/") {
                acc.currentPath += "|${props[2]}"
                acc.allDirs.add(acc.currentPath)
            }
            return@fold acc
        }

    fun getDirSizes(input: List<String>): List<Long> =
        foldFn(input).run {
            this.allDirs.toSet().map { dir ->
                this.files.filter { file -> file.path.startsWith(dir) }.sumOf { file -> file.size }
            }
        }

    fun part1(input: List<String>): Long = getDirSizes(input).filter { it <= 100000 }.sum()

    fun part2(input: List<String>): Long = getDirSizes(input).run { this.filter { it >= this.max() - 40000000 }.min() }

    val testInput = readInput("Day07_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
