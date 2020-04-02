class Solution {
    fun singleNumber(nums: IntArray) = nums.reduce { acc, i -> acc xor i }
}
