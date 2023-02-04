data class Valve(val id: String, val rate: Int, val vertexIds: List<String>)

fun makeValveSpecs(input: List<String>): Map<String, Valve> = input.map {
    val regex = """Valve ([\w\s]+) has flow rate=(\d+); tunnels? leads? to valves? ([A-Z, ]+)""".toRegex()
    val matchResult = regex.find(it)
    val (id, rate, vertexString) = matchResult!!.destructured
    val valve = Valve(id = id, rate = rate.toInt(), vertexIds = vertexString.split(", "))
    id to valve
}.toMap()

const val total_time = 10

data class State(val current: Valve, val valves: List<Valve>, val time: Int)

fun dfs(
    currentValve: Valve,
    currentTime: Int,
    totalPressure: Int,
    openValves: List<Valve>,
    valveMap: Map<String, Valve>,
    memoizedStates: MutableMap<State, Int>
): Int {
    println("Entering dfs. CurrentTime: $currentTime, currentValve: $currentValve")
    // Time is up: return the result. Note that this will bubble up the value from N levels deep right to the top.
    if (currentTime == total_time) {
        return totalPressure
    }

    // If state has been computed before return it
    val memoizationKey = State(currentValve, openValves, currentTime)
    when (val state = memoizedStates[memoizationKey]) {
        null -> {}
        else -> return state
    }

    println("Going to calculate newBest")
    val newBest = when {
        // open current valve rate > 0 and not already open. Stay in current and time increments
        currentValve.rate > 0 && !openValves.contains(currentValve) -> dfs(
            currentValve = currentValve,
            currentTime = currentTime + 1,
            totalPressure = totalPressure + openValves.sumOf { it.rate },
            openValves = openValves + currentValve,
            valveMap = valveMap,
            memoizedStates = memoizedStates
        )
        // find connected Valve with the maximum pressure by recursively calling the function on each child Valve and returning the maximum
        else -> currentValve.vertexIds.maxOf { child: String ->
            val childFromMap = valveMap[child]!!
            dfs(
                currentValve = childFromMap,
                currentTime = currentTime + 1,
                totalPressure = totalPressure + openValves.sumOf { it.rate },
                openValves = openValves,
                valveMap = valveMap,
                memoizedStates = memoizedStates
            )
        }
    }
    // add calculated state to the memoization Map
    println("After recursive call newBest. Result: $newBest")
    memoizedStates[memoizationKey] = newBest
    return newBest
}

fun main() {
    fun part1(input: List<String>): Int {
        val valves = makeValveSpecs(input)

        val entry = valves["AA"]!!

        // state and score for given state
        val memoizedState = mutableMapOf<State, Int>()

        return dfs(
            currentValve = entry,
            currentTime = 0,
            totalPressure = 0,
            openValves = listOf(),
            valveMap = valves,
            memoizedStates = memoizedState
        )
    }

    fun part2(input: List<String>): Int {
        val result = input.map { it.split(",") }
        println(result)
        return 1
    }

    val testInput = readInput("Day16_test_small")
    println(part1(testInput))
    //println(part2(testInput))

    val input = readInput("Day16")
    //println(part1(input))
    //println(part2(input))
}
