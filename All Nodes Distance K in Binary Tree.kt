import kotlin.math.pow

data class Node(val depth: Int, val width: Int, val value: Int, val remain: Int)
class Solution {
    fun distanceK(root: TreeNode?, target: TreeNode?, K: Int): List<Int> {
        root ?: return listOf()
        target ?: return listOf()
        
        val valueData = mutableMapOf(0 to root.`val`)
        
        var rootList = listOf(root to 0)
        var hasMore = true
        while (hasMore) {
            hasMore = false
            rootList = rootList.flatMap { item ->
                mutableListOf<Pair<TreeNode, Int>>().apply {
                    item.first.left?.let {
                        hasMore = true
                        add(it to 1 + item.second * 2)
                    }
                    item.first.right?.let {
                        hasMore = true
                        add(it to 2 + item.second * 2)
                    }
                }
            }

            valueData += rootList.map {
                it.second to it.first.`val`
            }
        }

        val targetIndex = valueData.filterValues { it == target.`val` }.keys.first()
        var depth = -1
        while (-1+2.0.pow(1+depth) <= targetIndex) {
            depth += 1
        }

        var width = (targetIndex - (-1+2.0.pow(depth))).toInt() * 2
        val startNodeList = List(1+depth) { depth - it }.mapNotNull {
            width /= 2
            valueData[-1+2.0.pow(it).toInt()+width]?.run {
                Node(it, width, this, K-depth+it)
            }
        }.reversed()
        
        val destinationList = mutableListOf<Int>()

        startNodeList.forEach { node ->
            var visitList = listOf(node)
            while (0 < visitList.firstOrNull()?.remain ?: 0) {
                visitList = visitList.flatMap {
                    val childDepth = 1+it.depth
                    val childWidth = it.width * 2
                    val childIndex = -1+2.0.pow(childDepth).toInt()+childWidth
                    listOfNotNull(valueData[childIndex]?.run {
                        Node(childDepth, childWidth, this, -1+it.remain)
                    }, valueData[1+childIndex]?.run {
                        Node(childDepth, 1+childWidth, this, -1+it.remain)
                    }).run {
                        if (-1+childDepth != node.depth) {
                            this
                        }
                        else {
                            toMutableList().apply {
                                removeAll { returnNote ->
                                    var returnValue = false
                                    startNodeList.forEach lit@ { startNode ->
                                        if (returnNote.width == startNode.width && returnNote.depth == startNode.depth) {
                                            returnValue = true
                                            return@lit
                                        }
                                    }
                                    returnValue
                                }
                            }
                        }
                    }
                }
            }
            if (0 == visitList.firstOrNull()?.remain) {
                destinationList.addAll(visitList.map { it.value })
            }
        }

        return destinationList
    }
}
