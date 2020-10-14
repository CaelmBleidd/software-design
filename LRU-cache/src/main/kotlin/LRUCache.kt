class LRUCache<K, V>(private val capacity: Int = 10) {
    private val cache = hashMapOf<K, Node<K, V>>()
    private var head: Node<K, V>? = null
    private var tail: Node<K, V>? = null
    val size: Int
        get() = cache.size

    init {
        assert(capacity > 0) { "Capacity must be positive" }
    }

    fun put(key: K, value: V) {
        val node = Node(key, value)
        if (key in cache) {
            assert(cache.size > 0)
            assert(cache[key] != null)

            val oldNode = cache.getValue(key)
            removeFromList(oldNode)
            assert(oldNode.prev == null && oldNode.next == null)
        } else if (size == capacity) {
            tail?.let { tail ->
                removeFromList(tail)
                assert(tail.prev == null && tail.next == null)

                cache -= tail.key
                assert(size < capacity)
            } ?: error("Capacity must be positive")
        }

        cache[key] = node
        assert(size <= capacity)

        moveToTop(node)
        assert(head === node)
    }

    private fun moveToTop(node: Node<K, V>) {
        node.prev = head
        head?.next = node
        head = node
        head?.next = null

        if (size == 1) tail = node

        assert(tail != null)
        assert(head === node)
    }

    private fun removeFromList(node: Node<K, V>) {
        if (node.prev == null) {
            tail = node.next
        }
        if (node.next == null) {
            head = node.prev
        }

        node.prev?.next = node.next
        node.next?.prev = node.prev

        assert(node.next?.prev !== node)
        assert(node.prev?.next !== node)
        node.next = null
        node.prev = null
    }

    fun find(key: K): V? {
        if (key !in cache) {
            return null
        }
        assert(size > 0)
        val node = cache.getValue(key)
        removeFromList(node)
        moveToTop(node)
        assert(head === node)
        return node.value
    }
}

data class Node<K, V>(val key: K, val value: V, var next: Node<K, V>? = null, var prev: Node<K, V>? = null) {
    override fun toString() = "key: $key, value: $value"
}

