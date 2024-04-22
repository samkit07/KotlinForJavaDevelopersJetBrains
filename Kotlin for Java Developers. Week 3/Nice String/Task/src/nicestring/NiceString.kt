package nicestring

fun String.isNice(): Boolean {
    val a = listOf("bu", "ba", "be").none { this.contains(it) }
    val b = this.count { it in listOf('a', 'e', 'i', 'o', 'u') } >= 3
//    val c = zipWithNext().any { it.first == it.second }  //another solution
    fun isDouble(): Boolean {
        this.forEachIndexed { index, c -> if (index != this.length - 1 && c == this[index + 1]) return true }
        return false
    }
    val c = isDouble()
    return listOf(a, b, c).count { it } >= 2
}