import me.andreaiacono.probabilisticdatastructures.utils.Primes
import java.util.stream.IntStream

class CountMinSketch(d: Int, private val w: Int) {
    private val count = Array(d) { LongArray(w) }
    private val hashers = (1..d).map { n -> Hasher(w, Primes.primes[n * 7 + 4], Primes.primes[3 * n + 3]) }

    fun add(item: String) = hashers.forEachIndexed { idx, hasher -> count[idx][hasher.hashCode(item) % w]++ }

    fun count(item: String) = hashers.mapIndexed { index, hasher -> count[index][hasher.hashCode(item) % w] }.min()

    private class Hasher(private val size: Int, private val base: Int, private val multiplier: Int) {
        fun hashCode(value: String) = value.map { it.code }
            .fold(base) { acc, curr -> acc * multiplier + curr }
            .mod(size)
    }
}
