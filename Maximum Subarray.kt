class Solution {
    fun maxSubArray(nums: IntArray): Int {
        var sum: Int? = null
        var biggest = nums[0]
        nums.forEach { i ->
            sum = (sum?.let {
                when {
                    it < 0 -> i
                    it + i < 0 -> null
                    else -> it + i
                }
            } ?: i).also {
                if (biggest < it) biggest = it
            }
        }
        return biggest
    }
}
