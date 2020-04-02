import kotlin.math.pow

class Solution {
    fun findNthDigit(n: Int): Int {
        fun digitLength(n: Int) = 9 * 10.0.pow(-1+n) * n
        var remainder = n
        var count = 0
        var length = 0
        while (length < remainder) {
            remainder -= length
            count += 1
            length = digitLength(count).toInt()
        }
        remainder -= 1
        val digit = 10.0.pow(-1+count)+(remainder / count)
        return (digit / 10.0.pow(count-1-remainder%count)).toInt() % 10
    }
}
