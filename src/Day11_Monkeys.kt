fun makeItem(i: Long): Item = Item(i, i)

fun makeItems(i: List<Long>): MutableList<Item> = i.map { makeItem(it) } as MutableList<Item>

fun makeMonkeysTest(): List<Monkey> {
    val m0 = Monkey(
        id = 0,
        operationFn = { wl -> wl * 19 },
        testFn = { wl -> wl % 23 == 0L },
        toMonkeyIdTrue = 2,
        toMonkeyIdFalse = 3,
        currentItems = makeItems(listOf(79, 98)),
        totalInpected = 0
    )
    val m1 = Monkey(
        id = 1,
        operationFn = { wl -> wl + 6 },
        testFn = { wl -> wl % 19 == 0L },
        toMonkeyIdTrue = 2,
        toMonkeyIdFalse = 0,
        currentItems = makeItems(listOf(54, 65, 75, 74)),
        totalInpected = 0
    )
    val m2 = Monkey(
        id = 2,
        operationFn = { wl -> wl * wl },
        testFn = { wl -> wl % 13 == 0L },
        toMonkeyIdTrue = 1,
        toMonkeyIdFalse = 3,
        currentItems = makeItems(listOf(79, 60, 97)),
        totalInpected = 0
    )
    val m3 = Monkey(
        id = 3,
        operationFn = { wl -> wl + 3 },
        testFn = { wl -> wl % 17 == 0L },
        toMonkeyIdTrue = 0,
        toMonkeyIdFalse = 1,
        currentItems = makeItems(listOf(74)),
        totalInpected = 0
    )
    return listOf(m0, m1, m2, m3)
}

fun makeMonkeys(): List<Monkey> {
    val m0 = Monkey(
        id = 0,
        operationFn = { wl -> wl * 19 },
        testFn = { wl -> wl % 7 == 0L },
        toMonkeyIdTrue = 2,
        toMonkeyIdFalse = 3,
        currentItems = makeItems(listOf(57, 58)),
        totalInpected = 0
    )
    val m1 = Monkey(
        id = 1,
        operationFn = { wl -> wl + 1 },
        testFn = { wl -> wl % 19 == 0L },
        toMonkeyIdTrue = 4,
        toMonkeyIdFalse = 6,
        currentItems = makeItems(listOf(66, 52, 59, 79, 94, 73)),
        totalInpected = 0
    )
    val m2 = Monkey(
        id = 2,
        operationFn = { wl -> wl + 6 },
        testFn = { wl -> wl % 5 == 0L },
        toMonkeyIdTrue = 7,
        toMonkeyIdFalse = 5,
        currentItems = makeItems(listOf(80)),
        totalInpected = 0
    )
    val m3 = Monkey(
        id = 3,
        operationFn = { wl -> wl + 5 },
        testFn = { wl -> wl % 11 == 0L },
        toMonkeyIdTrue = 5,
        toMonkeyIdFalse = 2,
        currentItems = makeItems(listOf(82, 81, 68, 66, 71, 83, 75, 97)),
        totalInpected = 0
    )
    val m4 = Monkey(
        id = 4,
        operationFn = { wl -> wl * wl },
        testFn = { wl -> wl % 17 == 0L },
        toMonkeyIdTrue = 0,
        toMonkeyIdFalse = 3,
        currentItems = makeItems(listOf(55, 52, 67, 70, 69, 94, 90)),
        totalInpected = 0
    )
    val m5 = Monkey(
        id = 5,
        operationFn = { wl -> wl + 7 },
        testFn = { wl -> wl % 13 == 0L },
        toMonkeyIdTrue = 1,
        toMonkeyIdFalse = 7,
        currentItems = makeItems(listOf(69, 85, 89, 91)),
        totalInpected = 0
    )
    val m6 = Monkey(
        id = 6,
        operationFn = { wl -> wl * 7 },
        testFn = { wl -> wl % 2 == 0L },
        toMonkeyIdTrue = 0,
        toMonkeyIdFalse = 4,
        currentItems = makeItems(listOf(75, 53, 73, 52, 75)),
        totalInpected = 0
    )
    val m7 = Monkey(
        id = 7,
        operationFn = { wl -> wl + 2 },
        testFn = { wl -> wl % 3 == 0L },
        toMonkeyIdTrue = 1,
        toMonkeyIdFalse = 6,
        currentItems = makeItems(listOf(94, 60, 79)),
        totalInpected = 0
    )
    return listOf(m0, m1, m2, m3, m4, m5, m6, m7)
}
