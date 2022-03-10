package com.ssmmhh.free2playgames.common


/**
 * Kotlin version of a java.util Queue
 * https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html
 */
class Queue<T>(list: List<T>) {

    var items: MutableList<T> = list.toMutableList()

    fun isEmpty(): Boolean = items.isEmpty()

    fun isNotEmpty(): Boolean = !isEmpty()

    fun count(): Int = items.count()

    override fun toString() = items.toString()

    fun add(element: T) {
        items.add(element)
    }

    @Throws(QueueException::class)
    fun remove(): T {
        if (this.isEmpty()) {
            throw QueueException("fun 'remove' threw an exception: Nothing to remove from the queue.")
        } else {
            return items.removeAt(0)
        }
    }

    fun remove(item: T): Boolean {
        return items.remove(item)
    }

    @Throws(QueueException::class)
    fun element(): T {
        if (this.isEmpty()) {
            throw QueueException("fun 'element' threw an exception: Nothing in the queue.")
        }
        return items[0]
    }

    fun offer(element: T): Boolean {
        return items.add(element)
    }

    fun poll(): T? {
        if (this.isEmpty()) return null
        return items.removeAt(0)
    }

    /**
     * Returns the head of queue (first element of list)
     * If queue is empty returns null
     */
    fun peek(): T? {
        if (this.isEmpty()) return null
        return items[0]
    }

    fun addAll(queue: Queue<T>) {
        this.items.addAll(queue.items)
    }

    fun clear() {
        items.removeAll { true }
    }

    class QueueException(message: String) : Exception(message)
}