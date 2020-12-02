import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MainTest {

    val testData = listOf(1721, 979, 366, 299, 675, 1456)

    @Test
    fun `when two numners add to 2020 the result is true`() {
        assertTrue(sumIs2020(Pair(1000, 1020)))
    }

    @Test
    fun `simple permutation for list of (1,2,3) is (1,2),(1,3),(2,1),(2,3),(3,1),(3,2))`() {
        val data = listOf(1, 2, 3)
        val expectedResult = listOf(Pair(1, 2), Pair(1, 3), Pair(2, 1), Pair(2, 3), Pair(3, 1), Pair(3, 2))
        assertEquals(expectedResult, simplePermutation(data))
    }

    @Test
    fun `test data gives an answer of 1721,299`() {
        val expectedResult = Pair(1721, 299)
        assertEquals(expectedResult, partOne(testData))
    }

    @Test
    fun `part one`() {
        val result = partOne(day1Data)
        println("Result is ${result.first * result.second}")
    }

    @Test
    fun `when three numners add to 2020 the result is true`() {
        assertTrue(sumIs2020(Triple(1000, 1010, 10)))
    }

    @Test
    fun `simple permutation3 for list of (1,2,3) is (1,2,3), (1,3,2), (2,1,3), (2,3,1), (3,1,2), (3,2,1)`() {
        val data = listOf(1, 2, 3)
        val expectedResult =
            listOf(Triple(1, 2, 3), Triple(1, 3, 2), Triple(2, 1, 3), Triple(2, 3, 1), Triple(3, 1, 2), Triple(3, 2, 1))
        assertEquals(expectedResult, simplePermutation3(data))
    }

    @Test
    fun `part two`() {
        val result = partTwo(day1Data)
        assertEquals(1450,result.first)
        assertEquals(43,result.second)
        assertEquals(527,result.third)
        println("Result is ${result.first * result.second * result.third}")
    }

    @Test
    fun `optimised part one`() {
        val result = optimisedPartOne(day1Data)
        println("Result is ${result.first * result.second}")
    }

    @Test
    fun `optimised part two`() {
        val result = optimisedPartTwo(day1Data)
        assertEquals(43,result.first)
        assertEquals(527,result.second)
        assertEquals(1450,result.third)
        println("Result is ${result.first * result.second * result.third}")
    }
}