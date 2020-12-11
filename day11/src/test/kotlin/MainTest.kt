import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = """
        L.LL.LL.LL
        LLLLLLL.LL
        L.L.L..L..
        LLLL.LL.LL
        L.LL.LL.LL
        L.LLLLL.LL
        ..L.L.....
        LLLLLLLLLL
        L.LLLLLL.L
        L.LLLLL.LL
    """.trimIndent().split("\n")

    @Test
    fun `getting lines from position 2,2`() {
        val grid = listOf(
            "abcde",
            "fghij",
            "klmno",
            "pqrst",
            "uvwxy"
        )
        var seats = grid.adjacentSeats (2,2)
        assertEquals(listOf("rw", "hc", "no", "lk", "ga", "qu", "ie", "sy"),seats)
    }
    @Test
    fun `occupied seats at position 2,2 is 3`() {
        val grid = listOf(
            "abcde",
            "fghij",
            "k#m#o",
            "pqr#t",
            "uvwxy"
        )
        var noOccupied = grid.noOfOccupiedAdjacentSeats (2,2)
        assertEquals(3,noOccupied)
    }
    @Test
    fun `transforming sample seats once results in correct new grid`() {
        val expectedResult = """
            #.##.##.##
            #######.##
            #.#.#..#..
            ####.##.##
            #.##.##.##
            #.#####.##
            ..#.#.....
            ##########
            #.######.#
            #.#####.##
        """.trimIndent().split("\n")
        val result = sampleData.transformSeats(partOneRule)
        assertEquals(expectedResult, result)
    }
    @Test
    fun `transforming sample seats until nothing changes results in correct new grid`() {
        val expectedResult = """
            #.#L.L#.##
            #LLL#LL.L#
            L.#.L..#..
            #L##.##.L#
            #.#L.LL.LL
            #.#L#L#.##
            ..L.L.....
            #L#L##L#L#
            #.LLLLLL.L
            #.#L#L#.##
        """.trimIndent().split("\n")
        val result = sampleData.transformUntilStable()
        assertEquals(expectedResult, result)
        assertEquals(37, result.noOfOccupiedSeats())
    }
    @Test
    fun `part one`() {
        val result = day11Data.transformUntilStable()
        assertEquals(2424, result.noOfOccupiedSeats())
    }

    @Test
    fun `first visible seat occupied`() {
        val sample1 = """
            .......#.
            ...#.....
            .#.......
            .........
            ..#L....#
            ....#....
            .........
            #........
            ...#.....
        """.trimIndent().split("\n")
        assertEquals(8, sample1.noOfVisibleOccupiedAdjacentSeats(3,4))

        val sample2 = """
            .............
            .L.L.#.#.#.#.
            .............
        """.trimIndent().split("\n")
        assertEquals(0, sample2.noOfVisibleOccupiedAdjacentSeats(1,1))
    }

    @Test
    fun `one transformation of seats using new part2 rules`() {
        val expectedResult = """
            #.##.##.##
            #######.##
            #.#.#..#..
            ####.##.##
            #.##.##.##
            #.#####.##
            ..#.#.....
            ##########
            #.######.#
            #.#####.##
        """.trimIndent().split("\n")
        val result = sampleData.transformSeats(partTwoRule)
        assertEquals(expectedResult, result)
        assertEquals(71, result.noOfOccupiedSeats())
    }
    @Test
    fun `transforming seats using new part2 rules`() {
        val expectedResult = """
            #.L#.L#.L#
            #LLLLLL.LL
            L.L.L..#..
            ##L#.#L.L#
            L.L#.LL.L#
            #.LLLL#.LL
            ..#.L.....
            LLL###LLL#
            #.LLLLL#.L
            #.L#LL#.L#
        """.trimIndent().split("\n")
        val result = sampleData.transformUntilStable2()
        assertEquals(expectedResult, result)
        assertEquals(26, result.noOfOccupiedSeats())
    }
    @Test
    fun `part two`() {
        val result = day11Data.transformUntilStable2()
        assertEquals(2208, result.noOfOccupiedSeats())
    }
}