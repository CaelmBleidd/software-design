import org.junit.Assert
import org.junit.Test
import java.util.*
import kotlin.math.abs


class LRUCacheTest {
    private class LinkedHashMapCache<K, V> internal constructor() :
        LinkedHashMap<K, V>(MAX_ENTRIES, 0.5f, true) {
        override fun removeEldestEntry(eldest: Map.Entry<K, V>): Boolean {
            return size > MAX_ENTRIES
        }

        companion object {
            private const val MAX_ENTRIES = 20
        }
    }

    @Test
    fun test_single_store() {
        val single = LRUCache<Int, Int>(1)
        Assert.assertEquals(0, single.size)
        Assert.assertNull(single.find(0))
        single.put(0, 1)
        Assert.assertEquals(Integer.valueOf(1), single.find(0))
        Assert.assertEquals(1, single.size)
    }

    @Test
    fun test_single_hit() {
        val single = LRUCache<Int, Int>(1)
        single.put(0, 1)
        single.put(1, 2)
        Assert.assertNull(single.find(0))
        Assert.assertEquals(Integer.valueOf(2), single.find(1))
        Assert.assertEquals(1, single.size)
    }

    @Test
    fun test_single_reassign() {
        val single = LRUCache<Int, Int>(1)
        single.put(0, 1)
        single.put(0, 2)
        Assert.assertNull(single.find(1))
        Assert.assertEquals(Integer.valueOf(2), single.find(0))
        Assert.assertEquals(1, single.size)
    }

    @Test
    fun test_multiple_store() {
        val cache = LRUCache<Int, Int>(4)
        cache.put(0, 5)
        cache.put(20, 21)
        cache.put(1, 8)
        cache.put(15, 12)
        Assert.assertNull(cache.find(4))
        Assert.assertEquals(Integer.valueOf(5), cache.find(0))
        Assert.assertEquals(Integer.valueOf(8), cache.find(1))
        Assert.assertEquals(Integer.valueOf(21), cache.find(20))
        Assert.assertEquals(Integer.valueOf(12), cache.find(15))
        Assert.assertEquals(4, cache.size)
    }

    @Test
    fun test_multiple_hits() {
        val cache = LRUCache<Int, Int>(4)
        cache.put(0, 5)
        cache.put(20, 21)
        cache.put(1, 8)
        cache.put(15, 12)
        cache.put(2, 5)
        cache.put(3, 4)
        cache.put(0, 2)
        Assert.assertNull(cache.find(1))
        Assert.assertNull(cache.find(20))
        Assert.assertEquals(Integer.valueOf(12), cache.find(15))
        Assert.assertEquals(Integer.valueOf(5), cache.find(2))
        Assert.assertEquals(Integer.valueOf(4), cache.find(3))
        Assert.assertEquals(Integer.valueOf(2), cache.find(0))
        Assert.assertEquals(4, cache.size)
    }

    @Test
    fun test_multiple_reassign() {
        val cache = LRUCache<Int, Int>(4)
        cache.put(0, 5)
        cache.put(20, 21)
        cache.put(0, 2)
        cache.put(0, 2)
        cache.put(20, 21)
        Assert.assertEquals(Integer.valueOf(2), cache.find(0))
        Assert.assertEquals(Integer.valueOf(21), cache.find(20))
        Assert.assertEquals(2, cache.size)
    }

    @Test
    fun test_multiple_access() {
        val cache = LRUCache<Int, Int>(4)
        cache.put(0, 5)
        cache.put(20, 21)
        cache.put(1, 8)
        cache.put(15, 12)
        cache.find(0)
        cache.find(20)
        cache.put(2, 5)
        cache.put(3, 4)
        Assert.assertNull(cache.find(1))
        Assert.assertNull(cache.find(15))
        Assert.assertEquals(Integer.valueOf(5), cache.find(0))
        Assert.assertEquals(Integer.valueOf(21), cache.find(20))
        Assert.assertEquals(Integer.valueOf(5), cache.find(2))
        Assert.assertEquals(Integer.valueOf(4), cache.find(3))
        Assert.assertEquals(4, cache.size)
    }

    @Test
    fun test_random() {
        val cache = LRUCache<Int, Double>(20)
        val expected: MutableMap<Int, Double> = LinkedHashMapCache()
        val rnd = Random()
        val keyArray: MutableList<Int> = ArrayList()
        val keyNumber = 100
        for (i in 0 until keyNumber) {
            keyArray.add(rnd.nextInt())
        }
        for (i in 0..999999) {
            val nextOperation: Int = rnd.nextInt() % 2
            val key = keyArray[abs(rnd.nextInt()) % keyNumber]
            if (nextOperation == 0) {
                val value: Double = rnd.nextDouble()
                cache.put(key, value)
                expected[key] = value
            } else if (nextOperation == 1) {
                Assert.assertEquals(expected[key], cache.find(key))
            }
            Assert.assertEquals(expected.size, cache.size)
        }
    }
}