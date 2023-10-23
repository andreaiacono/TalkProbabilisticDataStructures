package me.andreaiacono.probabilisticdatastructures

import CountMinSketch
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CountMinSketchKtTest {

    @Test
    fun shouldCountCorrectly() {
        val countMinSketch = CountMinSketch(4, 16)
        assertEquals(0, countMinSketch.count("test1"))

        countMinSketch.add("test1")
        assertEquals(1, countMinSketch.count("test1"))

        countMinSketch.add("test1")
        countMinSketch.add("test2")
        assertEquals(2, countMinSketch.count("test1"))
        assertEquals(1, countMinSketch.count("test2"))
    }
}