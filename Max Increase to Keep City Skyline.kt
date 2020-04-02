class Solution {
    fun maxIncreaseKeepingSkyline(grid: Array<IntArray>): Int {
        val oldSum = grid.fold(0) { sum, element ->
        sum + element.fold(0) { sum2, element2 -> sum2 + element2 }
    }
    val topLine = grid.first().mapIndexed { index, _ ->
        grid.map { it[index] }.max() ?: 0
    }
    val rightLine = grid.map { it.max() ?: 0 }

    for (index1 in 0 until grid.count()) { 
        for (index2 in 0 until grid.first().count()) {
            grid[index1][index2] = if (topLine[index1] < rightLine[index2]) topLine[index1] else rightLine[index2]
        }
    }


        return grid.fold(0) { sum, element ->
            sum + element.fold(0) { sum2, element2 -> sum2 + element2 }
        } - oldSum
    }
}
