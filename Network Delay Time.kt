class Solution {
    fun networkDelayTime(times: Array<IntArray>, N: Int, K: Int): Int {

        val nodeList: List<List<Pair<Int, Int>>> = List(N) { index ->
            times.filter {
                it[0] == 1+index
            }.map {
                Pair(it[1], it[2])
            }
        }

        val notVisitedList = (1..N).toMutableSet().apply { remove(K) }
        var workList = nodeList[-1+K]

        fun newWorkList(node: Int) = if (notVisitedList.contains(node)) {
            notVisitedList.remove(node)
            nodeList[-1+node]
        } else listOf()

        fun removeZeroDistance() {
            workList.find { 0 == it.second }?.let {
                workList = workList.flatMap {
                    if (0 == it.second) newWorkList(it.first)
                    else listOf(it)
                }
                removeZeroDistance()
            }
        }

        removeZeroDistance()

        var count = 0
        while (notVisitedList.isNotEmpty() && workList.isNotEmpty()) {
            count += 1
            workList = workList.flatMap {
                when {
                    1 < it.second -> listOf(Pair(it.first, -1+it.second))
                    else -> newWorkList(it.first)
                }
            }
            removeZeroDistance()
        }
        return if (notVisitedList.isEmpty()) count else -1
    }
}
