class Solution {
    fun groupAnagrams(strs: Array<String>) = mutableMapOf<String, MutableList<String>>().run {
        strs.forEach { item ->
            item.toCharArray().sorted().toString().let {
                this[it] = this[it]?.apply { add(item) } ?: mutableListOf(item)
            }
        }
        values.toList()
    }
}
