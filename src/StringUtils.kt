/**
 * Parse a string that is a (potentially) nested array of integers to a nested array of integers
 * [[2]] becomes arraylist arraylist 2
 * [1,1,3,1,1] becomes arraylist 1,1,3,1,1
 * [[1],[2,3,4]] becomes arraylist (arraylist 1, arraylist 2,3,4)
 */
fun parseStringToNestedArray(s: String): Any {
    var i = 0

    fun next(): Any {
        if (s[i] == '[') {
            i++
            val res = ArrayList<Any>()
            while(true) {
                if (s[i] == ']') {
                    i++
                    return res
                }
                res.add(next())
                if (s[i] == ']') {
                    i++
                    return res
                }
                check(s[i] == ',')
                i++
            }
        }
        check(s[i] in '0'..'9')
        var num = 0
        while (s[i] in '0'..'9') {
            num = num * 10 + (s[i] - '0')
            i++
        }
        return num
    }

    val res = next()
    check(i == s.length)
    return res
}
