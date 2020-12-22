import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    private val sampleData = """
        Player 1:
        9
        2
        6
        3
        1

        Player 2:
        5
        8
        4
        7
        10
    """.trimIndent().split("\n")

    @Test
    fun `convert sampleData into to lists of Ints`() {
        val cardDecks = sampleData.parse()
        assertEquals(listOf(9,2,6,3,1), cardDecks[0])
        assertEquals(listOf(5,8,4,7,10), cardDecks[1])
    }

    @Test
    fun `Part one sample data`() {
        val (_, deck2) = sampleData.process()
        println(deck2.score())
    }
    @Test
    fun `Part one`() {
        val (deck1a, _) = day22Data.process()
        assertEquals(35818, deck1a.score())
    }
    @Test
    fun `loop detection`() {
        val loopData = """
            Player 1:
            43
            19

            Player 2:
            2
            29
            14
        """.trimIndent().split("\n")

        val (winner, _, _) = loopData.process2()
        assertEquals("P1", winner)
    }
    @Test
    fun `Part two sample data`() {
        val (_, deck1, deck2) = sampleData.process2()
        assertEquals(0, deck1.score())
        assertEquals(291, deck2.score())
    }
    @Test
    fun `Part two full data`() {
        val (_, deck1, deck2) = day22Data.process2()
        assertEquals(34771, deck1.score())
        assertEquals(0, deck2.score())
    }
    @Test
    fun `Part two pauls data`() {
        val (_, deck1, deck2) = paulData.process2()
        assertEquals(0, deck1.score())
        assertEquals(32665, deck2.score())
    }
}