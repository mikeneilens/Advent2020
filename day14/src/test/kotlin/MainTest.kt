import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = """
        mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
        mem[8] = 11
        mem[7] = 101
        mem[8] = 0
    """.trimIndent().split("\n")
    @Test
    fun `parsing input`() {
        val expectedResult = listOf(
            Program(Mask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"),
            listOf(Instruction(8,11), Instruction(7,101),Instruction(8,0))),
            Program(Mask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"),
                listOf(Instruction(8,11), Instruction(7,101),Instruction(8,0))),
            )
        assertEquals(expectedResult, (sampleData + sampleData).parse() )
    }
    @Test
    fun `binary to integer`() {
        val result = "000000000000000000000000000001001001".bToLong()
        assertEquals(73, result)
    }
    @Test
    fun `integer to binary`() {
        val result = 73L.toBinary()
        assertEquals("000000000000000000000000000001001001", result)
    }
    @Test
    fun `applying masks`() {
        val mask = Mask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")
        val result1 = Instruction(8,11).applyMaskToValue(mask)
        assertEquals(Instruction(8,73), result1)
        val result2 = Instruction(7,101).applyMaskToValue(mask)
        assertEquals(Instruction(7,101), result2)
        val result3 = Instruction(8,0).applyMaskToValue(mask)
        assertEquals(Instruction(8,64), result3)
    }
    @Test
    fun `processing sample data program`() {
        val memory = partOne(sampleData)
        assertEquals(165, memory.values.sum())
    }
    @Test
    fun `part one`() {
        val memory = partOne(day14Data)
        assertEquals(7817357407588, memory.values.sum())
    }

    //part two
    @Test
    fun `a mask containing one X is converted to a list of maps containing two masks`() {
        val result:List<Mask> = Mask("00000X11111").floatingMasks
        val expectedResult:List<Mask> = listOf(Mask("XXXXX011111"),Mask("XXXXX111111"))
        assertEquals(expectedResult, result)
    }
    @Test
    fun `a mask containing two X is converted to a list of maps containing four masks`() {
        val result:List<Mask> = Mask("00000X11X11").floatingMasks
        val expectedResult:List<Mask> = listOf(Mask("XXXXX011011"),Mask("XXXXX011111"),Mask("XXXXX111011"),Mask("XXXXX111111"))
        assertEquals(expectedResult, result)
    }
    @Test
    fun `applying a mask to an address`() {
        val instruction = Instruction(42,100)
        val results = instruction.applyMaskToAddress(Mask("000000000000000000000000000000X1001X"))
        val expectedResults = listOf(
            Instruction(26, 100), Instruction(27, 100), Instruction(58, 100), Instruction(59, 100)
        )
        assertEquals(expectedResults, results)
    }
    @Test
    fun `processing sample data program for part two`() {
        val sampleData = """
            mask = 00000000000000000000000000000000X0XX
            mem[26] = 1
        """.trimIndent().split("\n")
        val memory = mutableMapOf<Long, Long>()
        sampleData.parse().forEach{program ->  program.process(memory, Instruction::updateMemoryUsingAddressMask)}

        assertEquals(listOf(16L,17L,18L,19L,24L,25L,26L,27L), memory.keys.toList())
    }
    @Test
    fun `processing sample full program for part two`() {
        val sampleData = """
            mask = 000000000000000000000000000000X1001X
            mem[42] = 100
            mask = 00000000000000000000000000000000X0XX
            mem[26] = 1
        """.trimIndent().split("\n")
        val memory = mutableMapOf<Long, Long>()
        sampleData.parse().forEach{program ->  program.process(memory, Instruction::updateMemoryUsingAddressMask)}

        assertEquals(208, memory.values.sum())
    }
    @Test
    fun `part two `() {
        val memory = partTwo(day14Data)
        assertEquals(4335927555692, memory.values.sum())
    }

}