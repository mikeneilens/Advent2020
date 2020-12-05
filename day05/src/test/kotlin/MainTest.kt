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
    fun `binary conversion works when string is Fs `() {
        assertEquals(44,"FBFBBFF".binaryToInteger("B"))
    }
    @Test
    fun `binary conversion works when string is Fs and Rs`() {
        assertEquals(567,"BFFFBBFRRR".binaryToInteger("BR"))
    }
    @Test
    fun `finding the seatId of a boarding pass`() {
        assertEquals(567,toSeatId("BFFFBBFRRR"))
        assertEquals(119,toSeatId("FFFBBBFRRR"))
        assertEquals(820,toSeatId("BBFFBBFRLL"))
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
        assertEquals(4,data.theMissingSeat())
    }
    @Test
    fun `part two`() {
        assertEquals(625,day05Data.theMissingSeat())
    }

}