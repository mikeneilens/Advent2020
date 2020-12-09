import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = (1L..25L).toList()
    val sampleData2 = (1L..25L).toList().filter{it != 20L} + 45L
    val sampleData3 = listOf(
        35L,
        20L,
        15L,
        25L,
        47L,
        40L,
        62L,
        55L,
        65L,
        95L,
        102L,
        117L,
        150L,
        182L,
        127L,
        219L,
        299L,
        277L,
        309L,
        576L,
    )
    @Test
    fun `combining (1,2,3,4) to find numbers that match 4 is (1,3) `() {
        val l = (1L..4L).toList()
        assertEquals(listOf(Pair(1L,3L)), combinedValuesEqualToLast(l))
    }
    @Test
    fun `26 and 49 is valid and 100 and 50  is not valud number at end of sample data`() {
        assertFalse( blockContainsError(sampleData + 26))
        assertFalse( blockContainsError(sampleData + 49))
        assertTrue( blockContainsError(sampleData + 100))
        assertTrue( blockContainsError(sampleData + 50))
    }
    @Test
    fun `26,64,66 is valid and 65 is not valud number at end of sample data2`() {
        assertFalse( blockContainsError(sampleData2 + 26))
        assertFalse( blockContainsError(sampleData2 + 64))
        assertFalse( blockContainsError(sampleData2 + 66))
        assertTrue( blockContainsError(sampleData2 + 65))
    }
    @Test
    fun `finding error in sample data3`() {
        assertEquals(127, checkForError(sampleData3,6))
    }
    @Test
    fun `converting a string of ints into a list of Ints`() {
        val data = """
            13
            47
            42
            30
            4
        """.trimIndent()
        assertEquals(listOf(13L,47L,42L,30L,4L),data.toListOfLongs())
    }
    @Test
    fun `part one`() {
        assertEquals(1639024365L, checkForError(day09Data.toListOfLongs(),26))
    }

    @Test
    fun `contiguous block starting at position 2 in sample3 sums to target of 127`() {
        val (min, max ) = sumUntilGreaterOrEqual(sampleData3, 127L, 2) ?: Pair(-1,-1)
        assertEquals( 2, min, )
        assertEquals(5, max )
    }

    @Test
    fun `finding continguous block that sums up to target in sample 3`() {
        val (min, max ) = findContiguousItemsMatching(sampleData3,127L)
        assertEquals( 15L, min)
        assertEquals( 47L, max)
    }

    @Test
    fun `part two`() {
        val (min, max ) = findContiguousItemsMatching(day09Data.toListOfLongs(),1639024365L)
        assertEquals( 66794732L, min)
        assertEquals( 152407508L, max)
        assertEquals( 219202240L, min + max)
    }
}