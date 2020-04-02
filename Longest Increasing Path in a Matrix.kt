class Solution {
    private var size = 0
    private val visited = mutableListOf<Pair<Int, Int>>()
    private val distances = mutableMapOf<Pair<Pair<Int, Int>, Pair<Int, Int>>, Int>()

    fun longestIncreasingPath(matrix: Array<IntArray>): Int {
        matrix.forEachIndexed { indexRow, col ->
            col.forEachIndexed { indexCol, item ->

                visited.add(indexRow to indexCol)
                var downList = 0
                var upList = 0

                if (1+indexCol < col.size) find(matrix, indexRow, indexCol, item, indexRow, 1+indexCol).let {
                    downList = it.first.takeIf { list -> downList < list } ?: downList
                    upList = it.second.takeIf { list -> upList < list } ?: upList
                }

                if ( 1+indexRow < matrix.size) find(matrix, indexRow, indexCol, item,  1+indexRow, indexCol).let {
                    downList = it.first.takeIf { list -> downList < list } ?: downList
                    upList = it.second.takeIf { list -> upList < list } ?: upList
                }

                val newSize = 1+downList+upList
                if (size < newSize) size = newSize
                if ((matrix.size - indexRow) * col.size - indexCol < 2*size) return size
            }
        }
        return size
    }

    fun find(matrix: Array<IntArray>, row: Int, col: Int, item: Int, indexRow: Int, indexCol: Int): Pair<Int, Int> {

        val newItem = matrix[indexRow][indexCol]
        val upList = if (item < newItem) find(matrix, row, col, indexRow, indexCol, true, 1).also {
            if (it.isNotEmpty()) {
                val key = makeKey(row to col, it.first())
                distances[key] = distances[key]?.takeIf { length -> it.size < length } ?: it.size
            }
        } else emptyList()
        val downList = if (newItem < item) find(matrix, row, col, indexRow, indexCol, false, 1).also {
            if (it.isNotEmpty()) {
                val key = makeKey(row to col, it.last())
                distances[key] = distances[key]?.takeIf { length -> it.size < length } ?: it.size
            }
        } else emptyList()
        return (downList.size to upList.size)
    }

    fun find(matrix: Array<IntArray>, row: Int, col: Int, indexRow: Int, indexCol: Int, up: Boolean, depth: Int): List<Pair<Int, Int>> {
        var list = listOf<Pair<Int, Int>>()

        if (0 <= visited.indexOf(indexRow to indexCol)) return list

        distances[makeKey(row to col,indexRow to indexCol)]?.let {
            if (depth < it) {
                return list
            }
        }
        val key = makeKey(row to col, indexRow to indexCol)
        distances[key] = distances[key]?.takeIf { length -> depth < length } ?: depth

        val item = matrix[indexRow][indexCol]

        val orders = mutableListOf<Triple<Int, Int, Int>>()
        val blocks = mutableListOf<Pair<Int, Int>>()
        var newRow = -1+indexRow
        var newCol = indexCol
        blocks.add(newRow to newCol)
        if (0 <= newRow) {
            val newItem = matrix[newRow][newCol]
            if (if (up) item < newItem else newItem < item) {
                orders.add(Triple(newRow, newCol, newItem))
                blocks.remove(newRow to newCol)
            }
        }

        newRow = 1+indexRow
        newCol = indexCol
        blocks.add(newRow to newCol)
        if (newRow < matrix.size) {
            val newItem = matrix[newRow][newCol]
            if (if (up) item < newItem else newItem < item) {
                orders.add(Triple(newRow, newCol, newItem))
                blocks.remove(newRow to newCol)
            }
        }

        newRow = indexRow
        newCol = -1+indexCol
        blocks.add(newRow to newCol)
        if (0 <= newCol) {
            val newItem = matrix[newRow][newCol]
            if (if (up) item < newItem else newItem < item) {
                orders.add(Triple(newRow, newCol, newItem))
                blocks.remove(newRow to newCol)
            }
        }

        newRow = indexRow
        newCol = 1+indexCol
        blocks.add(newRow to newCol)
        if (newCol < matrix[indexRow].size) {
            val newItem = matrix[newRow][newCol]
            if (if (up) item < newItem else newItem < item) {
                orders.add(Triple(newRow, newCol, newItem))
                blocks.remove(newRow to newCol)
            }
        }

        orders.sortBy { it.third }

        visited.add(indexRow to indexCol)
        visited.addAll(blocks)

        (if (up) orders else orders.reversed()).forEach {
            find(matrix, row, col, it.first, it.second, up, 1+depth).run {
                if (list.size < size) list = this
            }
        }
        visited.removeAll(blocks)
        visited.remove(indexRow to indexCol)

        return if (up) (list + listOf(indexRow to indexCol)).also {
            if (it.isNotEmpty()) {
                val key = makeKey(row to col, it.first())
                distances[key] = distances[key]?.takeIf { length -> it.size < length } ?: it.size
            }
        }
        else (listOf(indexRow to indexCol) + list).also {
            if (it.isNotEmpty()) {
                val key = makeKey(row to col, it.last())
                distances[key] = distances[key]?.takeIf { length -> it.size < length } ?: it.size
            }
        }
    }

    private fun makeKey(one: Pair<Int, Int>, two: Pair<Int, Int>) = when {
        one.first < two.first -> one to two
        two.first < one.first -> two to one
        one.second < two.second -> one to two
        else -> two to one
    }
}
