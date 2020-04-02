class Solution {
    fun setZeroes(matrix: Array<IntArray>): Unit {
        val zeroRows = mutableSetOf<Int>()
        matrix.forEachIndexed { col, ints ->
            var zero = false
            ints.forEachIndexed { row, item ->
                if (0 == item) {
                    zeroRows.add(row)
                    zero = true
                }
            }
            if (zero) matrix[col] = IntArray(ints.size) {0}
        }
        matrix.forEach { row ->
            zeroRows.forEach {
                row[it] = 0
            }
        }
    }
}
