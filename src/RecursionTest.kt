fun main() {
    fun recursiveSubstring(input: String, length: Int, res: String): String {
        println("Entering recursiveSubstring with input $input")
        if (res.length == length) {
            println("desired length reached")
            return res
        }

        if (input == "") {
            println("no characters left in input")
            return res
        }
        // note that result is updated here explicitly
        val result = recursiveSubstring(input.substring(1), length, res + input.substring(0, 1))
        println("After recursive call. Result: $result")
        return result
    }

    //val result = recursiveSubstring("Kylo Ren", 6, "")
    //println(result)

    fun recursiveSubstringTest(input: String, length: Int, res: MutableList<String>): MutableList<String> {
        println("Entering recursiveSubstring with input $input")
        if (res.size == length) {
            println("desired length reached")
            return res
        }

        if (input == "") {
            println("no characters left in input")
            return res
        }
        res.add(input.substring(0, 1))
        println("Added ${input.substring(0, 1)} to res")
        recursiveSubstringTest(input.substring(1), length, res)
        println("After recursive call. Result: $res")
        return res
    }

    val resultTest = recursiveSubstringTest("Kylo Ren", 6, mutableListOf()).joinToString("")
    println(resultTest)

}

