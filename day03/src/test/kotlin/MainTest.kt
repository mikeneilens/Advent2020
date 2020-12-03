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

        val routes = listOf(Pair(1,1), Pair(3,1), Pair(5,1), Pair(7,1), Pair(1,2))
        val results = sampleData.split("\n").treesEncountered(routes)
        assertEquals(2, results[0])
        assertEquals(7, results[1])
        assertEquals(3, results[2])
        assertEquals(4, results[3])
        assertEquals(2, results[4])
        val product:Long = results.reduce{a, v -> a * v}
        assertEquals(336, product)
    }

    @Test
    fun `part two`() {
        val routes = listOf(Pair(1,1), Pair(3,1), Pair(5,1), Pair(7,1), Pair(1,2))
        val results = dayThreeData.split("\n").treesEncountered(routes)
        val product:Long = results.reduce{a, v -> a * v}
        assertEquals(9709761600, product)
    }
}

