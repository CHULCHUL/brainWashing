class Solution {
    fun mirrorReflection(p: Int, q: Int): Int {
        val gcd = gcd(p, q)
        val dp = p / gcd
        val dq = q / gcd
        return if (0 == dp % 2) {
            if (0 == dp % dq) {
                if (0 == dp / dq % 2) 2 else 1
            }
            else {
                if (dp < dq) {
                    if (0 == dq % dp) {
                        if (0 == dq / dp % 2) 0 else 1
                    }
                    else 2
                }
                else if (0 == dq % 2) 0 else 2
            }
        }
        else if (0 == dq % 2) 0 else 1
    }
    
    private fun gcd(a: Int, b: Int): Int {
        var da = if (a < b) b else a
        var db = if (a < b) a else b
        while (0 != db) {
            val r = da % db
            da = db
            db = r
        }
        return da
    }
}
