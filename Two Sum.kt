class Solution {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        for (index1 in (0 until -1+nums.count())) {
            for (index2 in (1+index1 until nums.count())) {
            }
        }
        
        for (index1 in (0 until -1+nums.count())) {
            for (index2 in (1+index1 until nums.count())) {
                if (target == nums[index1] + nums[index2]) return intArrayOf(index1, index2)
            }
        }
        
        return intArrayOf()
    }
}
