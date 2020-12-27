import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun `part one`() {
        val result = partOne(day19Data)
        assertEquals(239, result)
    }

    @Test
    fun `part two`() {
        val result = partTwo(day19Data)
        assertEquals(405, result)
    }

}