package me.andreaiacono.probabilisticdatastructures

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BloomFilterTest {

    @Test
    fun shouldFailValidatingConstructor() {

        assertThrows<IllegalArgumentException> {
            BloomFilter(-1, 0.0)
        }
        assertThrows<IllegalArgumentException> {
            BloomFilter(0, 0.0)
        }
        assertThrows<IllegalArgumentException> {
            BloomFilter(100_000_001, 0.01)
        }
        assertThrows<IllegalArgumentException> {
            BloomFilter(1, 1.0000001)
        }
        assertThrows<IllegalArgumentException> {
            BloomFilter(1, -0.0000001)
        }
    }

    @Test
    fun shouldReturnCorrectlyInsertedElements() {

        // given
        val insertedItems = 100_000
        val bloomFilter = BloomFilter(insertedItems, 0.01)
        (1..insertedItems).forEach { bloomFilter.add("test-${it}") }

        // when
        val foundItems = (1..insertedItems).count { bloomFilter.contains("test-${it}") }

        // then
        assertEquals(insertedItems, foundItems)
    }

    @Test
    fun shouldReturnCorrectlyNotInsertedElements() {

        // given
        val insertedItems = 100_000
        val errorRate = 0.01
        val bloomFilter = BloomFilter(insertedItems, errorRate)
        (1..insertedItems).forEach { bloomFilter.add("test-${it}") }

        // when
        val notFoundItems = (1..insertedItems).count { !bloomFilter.contains("not-contained-${it}") }

        // then
        assertTrue(notFoundItems >= insertedItems * (1 - errorRate))
    }
}