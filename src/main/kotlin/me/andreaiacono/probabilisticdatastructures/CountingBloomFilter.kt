package me.andreaiacono.probabilisticdatastructures

import me.andreaiacono.probabilisticdatastructures.utils.Primes.primes
import java.util.stream.IntStream
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.ln
import kotlin.math.log2

class CountingBloomFilter(expectedSize: Int, errorRate: Double) {

    init {
        require(expectedSize in 1..100_000_000)
        require(errorRate in 0.0..1.0)
    }

    private val m: Int = -(expectedSize * ln(errorRate) / ln2squared).toInt()   // bitset size
    private val k = ceil(-log2(errorRate)).toInt()                              // number of hash functions
    private val counters = IntArray(m) { 0 }
    private val hashers = IntStream
        .rangeClosed(1, k)
        .mapToObj { n -> Hasher(primes[n + 4], primes[3 * n + 3]) }
        .toList()

    fun add(element: String) {
        hashers.forEach { hasher -> counters[abs(hasher.hashCode(element)) % m]++ }
    }

    fun delete(element: String) {
        hashers.forEach { hasher -> counters[abs(hasher.hashCode(element)) % m]-- }
    }

    fun contains(element: String): Boolean {
        return hashers.all { hasher -> counters[abs(hasher.hashCode(element)) % m] > 0 }
    }

    private class Hasher(private val base: Int, private val multiplier: Int) {
        fun hashCode(value: String): Int = value.map { it.code }.fold(base) { acc, curr -> acc * multiplier + curr }
    }
}