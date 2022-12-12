import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Extension function to get all index positions of a given element in a collection
 */
fun <E> Iterable<E>.indexesOf(e: E) = mapIndexedNotNull { index, elem -> index.takeIf { elem == e } }

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun findIndex(haystack2D: List<List<String>>, needle: String): MutableList<Int> =
    mutableListOf(-1, -1).apply {
        haystack2D.forEachIndexed { i, r ->
            r.forEachIndexed { j, c ->
                if (c == needle) {
                    this[0] = j
                    this[1] = i
                }
            }
        }
    }

/**
 * Dijkstra shortest path algorithm.
 * adapted from https://github.com/SebastianAigner/advent-of-code-2021/blob/master/src/main/kotlin/Day15.kt
 */
fun dijkstraSP(
    graph: List<Node<Int>>,
    source: Node<Int>,
    target: Node<Int>,
    filterNeighborsFn: (it: Node<Int>, u: Node<Int>) -> Boolean
): ArrayDeque<Node<Int>> {
    val q = mutableSetOf<Node<Int>>()
    val dist = mutableMapOf<Node<Int>, Int?>()
    val prev = mutableMapOf<Node<Int>, Node<Int>?>()
    for (vertex in graph) {
        dist[vertex] = Int.MAX_VALUE
        prev[vertex] = null
        q.add(vertex)
    }
    dist[source] = 0
    while (q.isNotEmpty()) {
        val u = q.minByOrNull { dist[it]!! }!!
        q.remove(u)
        if (u == target) break
        for (v in q.filter { filterNeighborsFn(it, u) }) {
            val alt = dist[u]!! + 1
            if (alt < dist[v]!!) {
                dist[v] = alt
                prev[v] = u
            }
        }
    }

    // all found.
    val s = ArrayDeque<Node<Int>>()
    var u: Node<Int>? = target
    if (prev[u] != null || u == source) {
        while (u != null) {
            s.addFirst(u)
            u = prev[u]
        }
    }

    return s
}

/**
 * Extension function that is like takeWhile, yet also takes the first element not making the test.
 */
fun <T> Iterable<T>.takeWhileInclusive(
    predicate: (T) -> Boolean
): List<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}
