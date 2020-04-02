class Solution {
    fun numberOfSubstrings(s: String): Int {
        val a = s.indexOf('a').takeIf { 0 <= it } ?: return 0
        val b = s.indexOf('b').takeIf { 0 <= it } ?: return 0
        val c = s.indexOf('c').takeIf { 0 <= it } ?: return 0
        val max = 1+if (a > b && a > c) a else if (b > c) b else c

        var count = 0
        s.forEachIndexed { index, c ->
            val start = if (3 < max-index) max-index else 3
            for (length in start..(s.length-index)) {
                val find = s.substring(index, index+length).takeIf {
                    it.contains("a") && it.contains("b") && it.contains("c")
                }?.also {
                    count += 1 + s.length - index - length
                }
                if (null != find) break
            }
        }
        return count
    }
}
