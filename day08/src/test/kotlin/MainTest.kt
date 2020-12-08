import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = """
        nop +0
        acc +1
        jmp +4
        acc +3
        jmp -3
        acc -99
        acc +1
        jmp -4
        acc +6
    """.trimIndent()
    @Test
    fun `parse sample data into a list of instructions`() {
        assertEquals(9,sampleData.parseIntoProgram().size)
    }
    @Test
    fun `process sample data until repeating instruction at same place`() {
        val (acc, finishedOK) = sampleData.parseIntoProgram().process()
        assertEquals(5, acc)
        assertEquals(false, finishedOK)
    }
    @Test
    fun `part one`() {
        val (acc, finishedOK) = day08Data.parseIntoProgram().process()
        assertEquals(1818, acc)
        assertEquals(false, finishedOK)
    }
    @Test
    fun `swapping jmp or nop using sample data`() {
        val (acc, finishedOK) = sampleData.parseIntoProgram().partTwo()
        assertEquals(8, acc)
    }
    @Test
    fun `part two`() {
        val (acc, finishedOK) = day08Data.parseIntoProgram().partTwo()
        assertEquals(631, acc)
    }
}
