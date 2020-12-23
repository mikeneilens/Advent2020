import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = "3,8,9,1,2,5,4,6,7".split(",").map{it.toInt()}
    @Test
    fun `getting first three cups`() {
        assertEquals(listOf(8,9,1), sampleData.pickCups(0))
        assertEquals(listOf(7,3,8), sampleData.pickCups(7))
    }
    @Test
    fun `cycling through indices`() {
        assertEquals(0, cycle(0))
        assertEquals(1, cycle(10))
        assertEquals(9, cycle(-1))
        assertEquals(8, cycle(-2))
    }
    @Test
    fun `finding destination`() {
        assertEquals(2, sampleData.destination(0, sampleData.pickCups(0) ))
        assertEquals(3, listOf(3,2,5,4,6,7,8,9,1).destination(2, listOf(4,6,7)))
        assertEquals(9, listOf(9,2,5,8,4,1,3,6,7).destination(5, listOf(3,6,7)))
    }
    @Test
    fun `doing one round`() {
        val (result, nextStart) = sampleData.processCups(0)
        assertEquals(listOf(3,2,8,9,1,5,4,6,7), result)
        assertEquals(1, nextStart)
        val (result2, nextStart2) = result.processCups(nextStart)
        assertEquals(listOf(3,2,5,4,6,7,8,9,1), result2)
        assertEquals(2, nextStart2)
        val (result3, nextStart3) = result2.processCups(nextStart2)
        assertEquals(listOf(3,4,6,7,2,5,8,9,1), result3)
        assertEquals(6, nextStart3)
    }
    @Test
    fun  `processing data 10 times`() {
        val result = sampleData.process(0)
        assertEquals(listOf(5,8,3,7,4,1,9,2,6),result)
    }
    @Test
    fun `converting list of ints to a string`() {
        assertEquals("92658374", listOf(5,8,3,7,4,1,9,2,6).result())
    }
    @Test
    fun  `processing data 100 times`() {
        val result = sampleData.process(0, 100)
        assertEquals("67384529",result.result())
    }
    @Test
    fun `part one`() {
        val result = listOf(3,2,6,5,1,9,4,7,8).process(0, 100)
        assertEquals("25368479",result.result())
    }
}

