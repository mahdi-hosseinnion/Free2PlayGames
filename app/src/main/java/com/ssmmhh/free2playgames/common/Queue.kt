package com.ssmmhh.free2playgames.common

import android.util.Log


/**
 * Kotlin version of a java.util Queue
 * https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html
 */
class Queue<T>(initialItems: List<T> = emptyList()) {

    private val _items: MutableList<T> = initialItems.toMutableList()
    val items: List<T> = _items


    fun isEmpty(): Boolean = _items.isEmpty()

    fun isNotEmpty(): Boolean = !isEmpty()

    fun count(): Int = _items.count()

    fun add(element: T) {
        _items.add(element)
    }

    fun remove(): T? {
        return if (this.isEmpty()) {
            null
        } else {
            _items.removeAt(0)
        }
    }

    fun remove(item: T): Boolean {
        return _items.remove(item)
    }


    fun element(): T? {
        if (this.isEmpty()) {
            return null
        }
        return _items[0]
    }

    fun offer(element: T): Boolean {
        return _items.add(element)
    }

    fun poll(): T? {
        if (this.isEmpty()) return null
        return _items.removeAt(0)
    }

    /**
     * Returns the head of queue (first element of list)
     * If queue is empty returns null
     */
    fun peek(): T? {
        if (this.isEmpty()) return null
        return _items[0]
    }

    fun addAll(queue: Queue<T>) {
        this._items.addAll(queue.items)
    }

    fun clear() {
        _items.removeAll { true }
    }

    override fun toString() = "Queue: $_items"

    override fun equals(other: Any?): Boolean {
        if (other is Queue<*>) {
            return _items == other._items
        }
        return false
    }

    override fun hashCode(): Int {
        var result = _items.hashCode()
        result = 31 * result + items.hashCode()
        return result
    }

}