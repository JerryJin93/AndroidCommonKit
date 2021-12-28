package com.jerryjin.kit.utils.collection

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 14:37
 *
 * GitHub: https://github.com/JerryJin93
 *
 * Blog:
 *
 * Email: jerry93@foxmail.com

 * WeChat: AcornLake
 *
 * Version: 1.0.0
 *
 * Description: Lru mutable list.
 */
open class LruMutableList<T>(
    private val list: MutableList<T>,
    private val maxRetainSize: Int = 500
) : MutableList<T> {
    override val size: Int
        get() = list.size

    override fun contains(element: T): Boolean =
        list.contains(element)

    private var _lru = false
    val lru
        get() = _lru

    override fun containsAll(elements: Collection<T>): Boolean = list.containsAll(elements)

    override fun get(index: Int): T = list[index]

    override fun indexOf(element: T): Int = list.indexOf(element)

    override fun isEmpty(): Boolean = list.isEmpty()

    override fun iterator(): MutableIterator<T> = list.iterator()

    override fun lastIndexOf(element: T): Int = list.lastIndexOf(element)

    private fun dropList() {
        _lru = if (size >= maxRetainSize) {
            drop(size - maxRetainSize + 1).run {
                list.clear()
                list.addAll(this)
            }
            true
        } else false
    }

    override fun add(element: T): Boolean =
        dropList().let {
            list.add(element)
        }

    override fun add(index: Int, element: T) {
        dropList()
        list.add(index, element)
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean =
        dropList().let {
            list.addAll(index, elements)
        }

    override fun addAll(elements: Collection<T>): Boolean =
        dropList().let {
            list.addAll(elements)
        }

    override fun clear() = list.clear()

    override fun listIterator(): MutableListIterator<T> = list.listIterator()

    override fun listIterator(index: Int): MutableListIterator<T> = list.listIterator(index)

    override fun remove(element: T): Boolean = list.remove(element)

    override fun removeAll(elements: Collection<T>): Boolean = list.removeAll(elements)

    override fun removeAt(index: Int): T = list.removeAt(index)

    override fun retainAll(elements: Collection<T>): Boolean = list.retainAll(elements)

    override fun set(index: Int, element: T): T = list.set(index, element)

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> =
        list.subList(fromIndex, toIndex)
}