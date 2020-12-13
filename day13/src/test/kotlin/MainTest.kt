import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    private val sampleData = """
        939
        7,13,x,x,59,x,31,19
    """.trimIndent().split("\n")
    @Test
    fun `parse data into an integer and list of integers`() {
        val (earliestDepart, busIds) = parseData(sampleData)
        assertEquals(939, earliestDepart)
        assertEquals(listOf(7,13,59,31,19), busIds)
    }
    @Test
    fun `finding best bus from sample data`() {
        val (earliestTime, _) = parseData(sampleData)
        val (busId, time) = partOne(sampleData)
        assertEquals(59, busId)
        assertEquals(944, time)
        assertEquals(295, busId * (time - earliestTime))
    }
    @Test
    fun `part one`() {
        val (earliestTime, _) = parseData(day13Data)
        val (busId, time) = partOne(day13Data)
        assertEquals(17, busId)
        assertEquals(1000059, time)
        assertEquals(119, busId * (time - earliestTime))
    }
    //part two
    @Test
    fun `parse data into a list of busIds and intervals`() {
        val expectedResult = listOf(
            BusInfo(7,0),
            BusInfo(13,1),
            BusInfo(59,4),
            BusInfo(31,6),
            BusInfo(19,7))
        val listOfBusInfo = parseData2(sampleData)
        assertEquals( expectedResult, listOfBusInfo)
    }

    @Test
    fun `find valid departure time for busses in sample data`() {
        assertEquals(1068781, partTwo(sampleData))
    }
    @Test
    fun `find valid departure time for busses in other sample data`() {
        assertEquals(754018, partTwo(listOf("","67,7,59,61")))
        assertEquals(779210, partTwo(listOf("","67,x,7,59,61")))
        assertEquals(1202161486, partTwo(listOf("","1789,37,47,1889")))
    }
    @Test
    fun `part two`() {
        assertEquals(1106724616194525, partTwo( day13Data))
    }
}