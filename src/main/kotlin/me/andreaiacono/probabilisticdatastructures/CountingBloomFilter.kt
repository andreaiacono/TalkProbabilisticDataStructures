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
    private val counters = ByteArray(m) { Byte.MIN_VALUE }
    private val hashers = IntStream
        .rangeClosed(1, k)
        .mapToObj { n -> Hasher(primes[n + 4], primes[3 * n + 3]) }
        .toList()

    fun add(item: String) = hashers.forEach { counters[abs(it.hashCode(item)) % m]++ }
    fun delete(item: String) = hashers.forEach { counters[abs(it.hashCode(item)) % m]-- }
    fun contains(item: String) = hashers.all { counters[abs(it.hashCode(item)) % m] > Byte.MIN_VALUE }

    private class Hasher(private val base: Int, private val multiplier: Int) {
        fun hashCode(value: String): Int = value.map { it.code }.fold(base) { acc, curr -> acc * multiplier + curr }
    }
}