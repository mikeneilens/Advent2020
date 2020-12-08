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
    fun `replacing first jmp or nop in the sample data`() {
        val x = sampleData.replaceNthJmpOrNop(1)
        assertEquals("jmp +0", sampleData.replaceNthJmpOrNop(1).split("\n")[0])
    }
    @Test
    fun `replacing 2nd jmp or nop in the sample data`() {
        assertEquals("nop +0", sampleData.replaceNthJmpOrNop(2).split("\n")[0])
        assertEquals("nop +4", sampleData.replaceNthJmpOrNop(2).split("\n")[2])
    }
    @Test
    fun `replacing 3rd jmp or nop in the sample data`() {
        assertEquals("nop +0", sampleData.replaceNthJmpOrNop(3).split("\n")[0])
        assertEquals("jmp +4", sampleData.replaceNthJmpOrNop(3).split("\n")[2])
        assertEquals("nop -3", sampleData.replaceNthJmpOrNop(3).split("\n")[4])

    }
    @Test
    fun `replacing 4th jmp or nop in the sample data`() {
        assertEquals("nop +0", sampleData.replaceNthJmpOrNop(4).split("\n")[0])
        assertEquals("jmp +4", sampleData.replaceNthJmpOrNop(4).split("\n")[2])
        assertEquals("jmp -3", sampleData.replaceNthJmpOrNop(4).split("\n")[4])
        assertEquals("nop -4", sampleData.replaceNthJmpOrNop(4).split("\n")[7])
    }
    @Test
    fun `swapping jmp or nop and finding result using sample data`() {
        val (acc, finishedOK) = part2(sampleData)
        assertEquals(8, acc)
    }
    @Test
    fun `part2`() {
        val (acc, finishedOK) = part2(day08Data)
        assertEquals(631, acc)
    }
}
