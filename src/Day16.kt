data class ValveSpec(val id: String, val rate: Int, val vertexIds: List<String>)

data class Valve(val id: String, val rate: Int, val isOpen: Boolean = false)

data class Graph(val nodes: List<Valve>, val edges: Map<Valve, List<Valve>>)

data class State(val currentValve: Valve, var timeLeft: Int, val flowSum: Int, val openValves: MutableList<Valve>)

fun makeValveSpecs(input: List<String>): List<ValveSpec> = input.map {
    val regex = """Valve ([\w\s]+) has flow rate=(\d+); tunnels? leads? to valves? ([A-Z, ]+)""".toRegex()
    val matchResult = regex.find(it)
    val (id, rate, vertexString) = matchResult!!.destructured
    ValveSpec(id = id, rate = rate.toInt(), vertexIds = vertexString.split(", "))
}

fun findPaths(
    graph: Map<Valve, List<Valve>>,
    current: Valve,
    target: Valve,
    path: MutableList<Valve>,
    allPaths: MutableList<List<Valve>>,
    visited: HashSet<Valve>
) {
    path.add(current)
    visited.add(current)
    if (current == target) {
        allPaths.add(path.toList())
    } else {
        for (next in graph[current]!!) {
            if (!visited.contains(next)) {
                findPaths(graph, next, target, path, allPaths, visited)
            }
        }
    }
    path.removeAt(path.size - 1)
    visited.remove(current)
}


fun main() {
    fun part1(input: List<String>): Int {
        val valveSpecs = makeValveSpecs(input)
        println(valveSpecs)

        // create the vertices (valves)
        val vertices = valveSpecs.map { Valve(id = it.id, rate = it.rate) }

        //  create edges as a Map
        val allEdges = vertices.mapNotNull { fromVertex ->
            val valve = valveSpecs.find { valveSpec -> valveSpec.id == fromVertex.id }
            val edges = valve?.vertexIds?.mapNotNull { toVertexId ->
                vertices.find { v -> v.id == toVertexId }
            }.orEmpty()
            fromVertex to edges
        }.toMap()

        val graph = Graph(vertices, allEdges)
        println(graph)
        val source = vertices.find { v -> v.id == "AA" }
        val target = vertices.find { v -> v.id == "HH" }

        val allPaths = mutableListOf<List<Valve>>()
        findPaths(graph.edges, source!!, target!!, mutableListOf(), allPaths, HashSet())
        println("result, there are ${allPaths.size} paths found. They are $allPaths")
//        source?.let { graph.distinctPathsAllVisited(it) }
//            ?.forEachIndexed { index, path -> println("nr $index path $path") }

        return 1
    }

    fun part2(input: List<String>): Int {
        val result = input.map { it.split(",") }
        println(result)
        return 1
    }

    val testInput = readInput("Day16_test")
    println(part1(testInput))
    //println(part2(testInput))

    val input = readInput("Day16")
    //println(part1(input))
    //println(part2(input))
}
