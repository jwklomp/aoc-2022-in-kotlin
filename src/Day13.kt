fun main() {

    /**
     * Custom compare to compare nested arrays based on the integers in them.
     */
    fun compareFn(left: Any, right: Any): Int {
        // both integers
        if (left is Int && right is Int) return left.compareTo(right)
        // both lists
        if (left is List<*> && right is List<*>) {
            var i = 0
            while (i < left.size && i < right.size) {
                val compareResult = compareFn(left[i]!!, right[i]!!)
                if (compareResult != 0) return compareResult
                i++
            }
            return left.size.compareTo(right.size)
        }
        // Int vs List
        if (left is Int) return compareFn(listOf(left), right)
        // List vs Int
        if (right is Int) return compareFn(left, listOf(right))
        error("illegal state")
    }

    fun part1(input: List<String>): Int =
        input
            .asSequence()
            .filter { it != "" }
            .map { parseStringToNestedArray(it) }
            .chunked(2)
            .mapIndexed { index, it -> if (compareFn(it.first(), it.last()) == -1) index + 1 else 0 }
            .sum()

    fun part2(input: List<String>): Int {

        val dv1 = parseStringToNestedArray("[[2]]")
        val dv2 = parseStringToNestedArray("[[6]]")

        val list = (input
            .filter { it != "" }
            .map { parseStringToNestedArray(it) } + listOf(dv1, dv2))
            .sortedWith { a, b -> compareFn(a, b) }

        val index1 = list.indexOf(dv1) + 1
        val index2 = list.indexOf(dv2) + 1
        return (index1 * index2)
    }

    val testInput = readInput("Day13_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
