package net.kibotu.logger

internal data class SynchronizedValue<T>(private var t: T? = null) {

    private val lock = Any()

    fun set(t: T) {
        synchronized(lock) {
            this.t = t
        }
    }

    fun get(): T? {
        synchronized(lock) {
            return t
        }
    }
}