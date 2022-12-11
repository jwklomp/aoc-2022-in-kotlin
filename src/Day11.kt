data class Item(val id: Long, var worryLevel: Long)
data class Monkey(
    val id: Int,
    val operationFn: (Long) -> Long,
    val testFn: (Long) -> Boolean,
    val toMonkeyIdTrue: Int,
    val toMonkeyIdFalse: Int,
    var currentItems: MutableList<Item>,
    var totalInpected: Long
)

val products = listOf(7, 19, 5, 11, 17, 13, 2, 3) // for test listOf(23, 19, 13, 17)
val productOfMod = products.reduce { acc, i -> acc * i }

fun main() {
    fun doThrowRound(monkeys: List<Monkey>) {
        monkeys.forEach { activeMonkey ->
            activeMonkey.currentItems.forEach { currentItem ->
                var newWl = activeMonkey.operationFn(currentItem.worryLevel)
                newWl %= productOfMod // Important. Many Bothans died to bring us this information..

                currentItem.worryLevel = newWl
                val toMonkeyId =
                    if (activeMonkey.testFn(newWl)) activeMonkey.toMonkeyIdTrue else activeMonkey.toMonkeyIdFalse
                monkeys[toMonkeyId].currentItems.add(currentItem)
                activeMonkey.totalInpected += 1
            }
            activeMonkey.currentItems = mutableListOf()
        }
    }

    fun run(monkeys: List<Monkey>): Long =
        repeat(10000) { doThrowRound(monkeys) }
            .let { monkeys.map { it.totalInpected }.sortedDescending().take(2).run { this.first() * this.last() } }

    val monkeysTest: List<Monkey> = makeMonkeysTest()
    println(run(monkeysTest))

    val monkeys: List<Monkey> = makeMonkeys()
    println(run(monkeys))
}


