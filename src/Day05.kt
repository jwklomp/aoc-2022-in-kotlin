import java.util.Stack

typealias CargoStack = List<Stack<String>>

fun main() {

    fun elementsToStack(elements: List<String>): Stack<String> {
        val stack = Stack<String>()
        stack.addAll(elements)
        return stack
    }

    fun makeElements(initialStackList: List<String>): MutableList<MutableList<String>> {
        //val indexPositions = listOf(1, 5, 9)
        val indexPositions = listOf(1, 5, 9, 13, 17, 21, 25, 29, 33)
        val reversed = initialStackList.reversed()
        val stackElements = indexPositions
            .map { i: Int ->
                reversed.map { line: String -> if (line.length >= i) line.substring(i, i + 1) else null }
                    .filterNot { it.isNullOrBlank() }
            }
        return stackElements as MutableList<MutableList<String>>
    }

    fun makeInitialStacks(initialStackList: List<String>): CargoStack {
        val stackElements = makeElements(initialStackList)
        return stackElements.map { elementsToStack(it)}
    }

    fun modifyStacks(stacks: CargoStack, move: List<String>) {
        val amount = move[1].toInt()
        val fromIndex = move[3].toInt() - 1
        val toIndex = move[5].toInt() - 1
        repeat(amount) {
            val element = stacks[fromIndex].pop();
            stacks[toIndex].push(element)
        }
    }

    fun modifyLists(lists: MutableList<MutableList<String>>, move: List<String>) {
        val amount = move[1].toInt()
        val fromIndex = move[3].toInt() - 1
        val toIndex = move[5].toInt() - 1
        val elementsToMove = lists[fromIndex].takeLast(amount)
        lists[toIndex].addAll(elementsToMove)
        val old = lists[fromIndex].dropLast(amount)
        lists[fromIndex] = old.toMutableList()
    }

    fun part1(stacksInput: List<String>, movesInput: List<String>): String {
        val stacks = makeInitialStacks(stacksInput)
        val moves = movesInput.map { it.split(" ") }
        moves.forEach { modifyStacks(stacks, it) }
        val answer = stacks.map { it.peek() }
        return answer.joinToString(separator = "")
    }

    fun part2(stacksInput: List<String>, movesInput: List<String>): String {
        val cargoLists = makeElements(stacksInput)
        val moves = movesInput.map { it.split(" ") }
        moves.forEach { modifyLists(cargoLists, it) }
        val answer = cargoLists.map { it.last()}
        return answer.joinToString(separator = "")
    }

    val testStacks = readInput("Day05_test_stacks")
    val testMoves = readInput("Day05_test_moves")
    //println(part1(testStacks, testMoves))
    //println(part2(testStacks, testMoves))

    val stacks = readInput("Day05_stacks")
    val moves = readInput("Day05_moves")
    //println(part1(stacks, moves))
    println(part2(stacks, moves))
}
