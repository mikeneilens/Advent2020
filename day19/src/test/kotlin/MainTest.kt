import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData1 = """
            0: 1 2
            1: "a"
            2: 1 3 | 3 1
            3: "b"
        """.trimIndent().split("\n")
    @Test
    fun `product of a line containing a reference to another line and another line`() {
        val result1 = "0:(1)(2)".product("(2):(1)(3)|(3)(1)")
        assertEquals("0:(1)(1)(3)|(1)(3)(1)", result1)

        val result2 = "(0):(1)(2)".product("""(1):a""")
        assertEquals("""(0):a(2)""", result2)

        val result3 = "(2):(1)(3)|(3)(1)".product("""(1):a""")?.product("""(3):b""")
        assertEquals("(2):ab|ba", result3)
    }
    @Test
    fun `checking if a line only contains letters`() {
        assertFalse("0: 1 2".onlyLetters())
        assertFalse("0: 1 a".onlyLetters())
        assertTrue("0: b a".onlyLetters())
        assertTrue("0: b a | a b".onlyLetters())
    }
    @Test
    fun `finding one line containing letters and substituting it into each line`() {
        val sampleData1 = """
            0: 1 2
            1: a
            2: 1 3 | 3 1
            3: b
        """.trimIndent().split("\n").encloseNumbers()
        val expectedResult  = """
            0: a 2
            2: a 3 | 3 a
            3: b
        """.trimIndent().split("\n").encloseNumbers()
        assertEquals(expectedResult, sampleData1.loop())
    }
    @Test
    fun `repeating finding line containing letters and substituting it into each line`() {
        val sampleData1 = """
            0: 1 2
            1: a
            2: 1 3 | 3 1
            3: b
        """.trimIndent().split("\n").encloseNumbers()
        val expectedResult  = """
            (0):aab|aba
        """.trimIndent().split("\n")
        assertEquals(expectedResult, sampleData1.process())
    }
    @Test
    fun `repeating finding line containing letters and substituting it into each line using second sample`() {
        val sampleData1 = """
            0: 4 1 5
            1: 2 3 | 3 2
            2: 4 4 | 5 5
            3: 4 5 | 5 4
            4: a
            5: b
        """.trimIndent().split("\n").encloseNumbers()
        assertTrue(sampleData1.process()[0].contains("aaaabb"))
        assertTrue(sampleData1.process()[0].contains("aaabab"))
        assertTrue(sampleData1.process()[0].contains("abbabb"))
        assertTrue(sampleData1.process()[0].contains("abbbab"))
        assertTrue(sampleData1.process()[0].contains("aabaab"))
        assertTrue(sampleData1.process()[0].contains("aabbbb"))
        assertTrue(sampleData1.process()[0].contains("abaaab"))
        assertTrue(sampleData1.process()[0].contains("ababbb"))
    }
    @Test
    fun `find patterns using real data`() {//takes too long to find last couple of substitutions!
        val result = day19Data.encloseNumbers().process()
    }
}