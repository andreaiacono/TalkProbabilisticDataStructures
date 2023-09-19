package me.andreaiacono.probabilisticdatastructures

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CountingBloomFilterTest {

    @Test
    fun shouldFailValidatingConstructor() {

        assertThrows<IllegalArgumentException> {
            CountingBloomFilter(-1, 0.0)
        }
        assertThrows<IllegalArgumentException> {
            CountingBloomFilter(0, 0.0)
        }
        assertThrows<IllegalArgumentException> {
            CountingBloomFilter(100_000_001, 0.01)
        }
        assertThrows<IllegalArgumentException> {
            CountingBloomFilter(1, 1.0000001)
        }
        assertThrows<IllegalArgumentException> {
            CountingBloomFilter(1, -0.0000001)
        }
    }


    @Test
    fun shouldNotContainElementAfterRemoval() {
        val countingBloomFilter = CountingBloomFilter(100, 0.01)
        assertFalse(countingBloomFilter.contains("test"))

        countingBloomFilter.add("test")
        assertTrue(countingBloomFilter.contains("test"))

        countingBloomFilter.delete("test")
        assertFalse(countingBloomFilter.contains("test"))
    }

    @Test
    fun shouldReturnCorrectlyInsertedElements() {

        // given
        val insertedItems = 100_000
        val countingBloomFilter = CountingBloomFilter(insertedItems, 0.01)
        (1..insertedItems).forEach { countingBloomFilter.add("test-${it}") }

        // when
        val foundItems = (1..insertedItems).count { countingBloomFilter.contains("test-${it}") }

        // then
        assertEquals(insertedItems, foundItems)
    }

    @Test
    fun shouldReturnCorrectlyNotInsertedElements() {

        // given
        val insertedItems = 100_000
        val errorRate = 0.01
        val countingBloomFilter = CountingBloomFilter(insertedItems, errorRate)
        (1..insertedItems).forEach { countingBloomFilter.add("test-${it}") }

        // when
        val notFoundItems = (1..insertedItems).count { !countingBloomFilter.contains("not-contained-${it}") }

        // then
        assertTrue(notFoundItems >= insertedItems * (1 - errorRate))
    }
}