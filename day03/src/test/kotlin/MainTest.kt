import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData= """
        ..##.......
        #...#...#..
        .#....#..#.
        ..#.#...#.#
        .#...##..#.
        ..#.##.....
        .#.#.#....#
        .#........#
        #.##...#...
        #...##....#
        .#..#...#.#
    """.trimIndent()
    @Test
    fun `finding the contents of a position where the position is shorter than the length of a string`() {
        val data = "123457"
        val result = data.dataAt(3)
        assertEquals('4', result)
    }
    @Test
    fun `finding the contents of a position where the position is the length of a string`() {
        val data = "1234567"
        val result = data.dataAt(7)
        assertEquals('1', result)
    }
    @Test
    fun `finding the contents of a position where the position is longer than the length of a string`() {
        val data = "1234567"
        val result = data.dataAt(25)
        assertEquals('5', result)
    }
    @Test
    fun `finding the contents of a position in a string array`() {
        val data = listOf("1234567"
                                     ,"8901234"
                                     ,"5678901")
        val result = data.dataAt(4,1)
        assertEquals('2', result)
    }
    @Test
    fun `position 3 1 is a space in the test data`() {
        assertTrue(sampleData.split("\n").dataAt(3,1).isSpace())
    }
    @Test
    fun `position 6 2 is a space in the test data`() {
        assertTrue(sampleData.split("\n").dataAt(6,2).isTree())
    }
    @Test
    fun `trees encountered in the sample data is 7`() {
        assertEquals(7, sampleData.split("\n").treesEncountered(3,1))
    }
    @Test
    fun `part one`() {
        assertEquals(278,  dayThreeData.split("\n").treesEncountered(3,1))
    }

    @Test
    fun `trees encountered in the sample data for different slopes`() {
        //        Right 1, down 1. 2
        //        Right 3, down 1. (This is the slope you already checked.) 7
        //        Right 5, down 1. 3
        //        Right 7, down 1. 4
        //        Right 1, down 2. 2

        val result1 = sampleData.split("\n").treesEncountered(1,1)
        val result2 = sampleData.split("\n").treesEncountered(3,1)
        val result3 = sampleData.split("\n").treesEncountered(5,1)
        val result4 = sampleData.split("\n").treesEncountered(7,1)
        val result5 = sampleData.split("\n").treesEncountered(1,2)
        assertEquals(2, result1)
        assertEquals(7, result2)
        assertEquals(3, result3)
        assertEquals(4, result4)
        assertEquals(2, result5)
        assertEquals(336, result1 * result2 * result3 * result4 * result5)
    }

    @Test
    fun `part two`() {
        val result1 = dayThreeData.split("\n").treesEncountered(1,1)
        val result2 = dayThreeData.split("\n").treesEncountered(3,1)
        val result3 = dayThreeData.split("\n").treesEncountered(5,1)
        val result4 = dayThreeData.split("\n").treesEncountered(7,1)
        val result5 = dayThreeData.split("\n").treesEncountered(1,2)
        assertEquals(9709761600, result1 * result2 * result3 * result4 * result5)
    }
}

