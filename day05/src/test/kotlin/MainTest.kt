import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `binary conversion works when string is 1s and 0s`() {
        assertEquals(0,"0".binaryToInteger())
        assertEquals(1,"1".binaryToInteger())
        assertEquals(2,"10".binaryToInteger())
        assertEquals(3,"11".binaryToInteger())
        assertEquals(4,"100".binaryToInteger())

    }
    @Test
    fun `binary conversion works when string is Fs and Rs`() {
        assertEquals(44,"FBFBBFF".binaryToInteger('B' ))
    }
    @Test
    fun `finding the row of a boarding pass`() {
        assertEquals(44,"FBFBBFFLRL".toRow())
    }
    @Test
    fun `finding the column of a boarding pass`() {
        assertEquals(5,"FBFBBFFRLR".toColumn())
    }
    @Test
    fun `finding the seatId of a boarding pass`() {
        assertEquals(567,"BFFFBBFRRR".toSeatId())
        assertEquals(119,"FFFBBBFRRR".toSeatId())
        assertEquals(820,"BBFFBBFRLL".toSeatId())
    }
    @Test
    fun `finding the  max seatId of boarding passes`() {
        val sampleData = """
            BFFFBBFRRR
            BBFFBBFRLL
            FFFBBBFRRR""".trimIndent()
        assertEquals(820,sampleData.maxSeatId())
    }
    @Test
    fun `part one`() {
        assertEquals(892,day05Data.maxSeatId())
    }
    @Test
    fun `find missing seat`() {
        val data =  listOf(2,3,5,6,7,8)
        assertEquals(4,data.findMissingSeat())
    }
    @Test
    fun `part two`() {
        assertEquals(625,day05Data.findMissingSeat())
    }

}