package me.andreaiacono.probabilisticdatastructures

import me.andreaiacono.probabilisticdatastructures.utils.Primes.primes
import java.util.*
import java.util.stream.IntStream
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.ln
import kotlin.math.log2

const val ln2squared = 0.4804530139

class BloomFilter(expectedSize: Int, errorRate: Double) {

    init {
        require(expectedSize in 1..100_000_000)
        require(errorRate in 0.0..1.0)
    }

    private val m: Int = -(expectedSize * ln(errorRate) / ln2squared).toInt()   // bitset size
    private val k = ceil(-log2(errorRate)).toInt()                              // number of hash functions
    private val bitSet = BitSet(m)
    private val hashers = (1..k).map { n -> Hasher(primes[n + 4], primes[3 * n + 3]) }

    fun contains(item: String) = hashers.all { hasher -> bitSet.get(abs(hasher.hashCode(item)) % m) }

    fun add(item: String) = hashers.forEach { hasher -> bitSet.set(abs(hasher.hashCode(item)) % m, true) }

    private class Hasher(private val base: Int, private val multiplier: Int) {
        fun hashCode(value: String): Int = value
            .map { it.code }
            .fold(base) { acc, curr -> acc * multiplier + curr }
    }
}