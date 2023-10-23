import kotlin.math.ln
import kotlin.math.max
import kotlin.math.pow

class HyperLogLog {
    private val p: Int = 4
    private val m: Int = 2.0.pow(p.toDouble()).toInt()
    private val alpha = 0.673
    private val buckets = IntArray(m)

    fun add(item: String) {
        val hash = item.hashCode()
        val index = hash.ushr(32 - p)
        val leadingZeroes = Integer.numberOfLeadingZeros(hash shl p)
        buckets[index] = max(buckets[index], leadingZeroes)
    }

    fun count(): Long {
        val harmonicMean = buckets.sumOf { 1.0 / (1 shl it) }
        val estimate = alpha * (m * m).toDouble() / harmonicMean
        return if (estimate <= 5.0 / 2.0 * m) {
            (m * ln(m.toDouble() / estimate)).toLong()
        } else {
            estimate.toLong()
        }
    }
}
