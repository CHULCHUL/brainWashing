class Solution {
    fun validTicTacToe(board: Array<String>): Boolean {
        val newBoard = board.flatMap {
            (0 until it.length).map { index ->
                it[index]
            }
        }

        var countOfX = 0
        var countOfO = 0
        newBoard.forEach {
            when (it) {
                'X' -> countOfX += 1
                'O' -> countOfO += 1
            }
        }

        if (countOfX < countOfO || countOfO < -1 + countOfX) return false

        val lines = listOf(listOf(0,1,2), listOf(3,4,5), listOf(6,7,8), 
                listOf(0,3,6), listOf(1,4,7), listOf(2,5,8), 
                listOf(0,4,8), listOf(2,4,6))
        lines.forEach { 
            val keyValue = newBoard[it[0]]
            if (keyValue == newBoard[it[1]] && keyValue == newBoard[it[2]]) {
                when (keyValue) {
                    'X' -> if (countOfO == countOfX) return false
                    'O' -> if (countOfO < countOfX) return false
                }
            }
        }
        
        return true
    }
}
