import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `with 0 3 6 next number is 0`() {
        val testList = listOf(
            Pair(0, listOf(0,3,6)),
            Pair(3,listOf(0,3,6,0)),
            Pair(3,listOf(0,3,6,0,3)),
            Pair(1,listOf(0,3,6,0,3,3)),
            Pair(0,listOf(0,3,6,0,3,3,1)),
            Pair(4,listOf(0,3,6,0,3,3,1,0)),
            Pair(0,listOf(0,3,6,0,3,3,1,0,4)),
        )
       testList.forEach{ (result, input) ->
            assertEquals(result, nextNumber(input))
        }

    }
    @Test
    fun `number at index 2020 is 436`() {
        val result:Int = numberAt(2020, listOf(0,3,6))
        assertEquals(436, result)
    }
    @Test
    fun `part one`() {
        val result = numberAt(2020, listOf(2,1,10,11,0,6))
        assertEquals(232, result)
    }
    //part two
    @Test
    fun `with 0 3 6 next number is 0 for part two`() {
        val map = mutableMapOf(0 to 0,3 to 1)
        val lastNumber = 6
        assertEquals(0, updateMapAndReturnNewValue(lastNumber,2,map))
        assertEquals(2, map[6])
        assertEquals(3, updateMapAndReturnNewValue(0,3,map))
        assertEquals(3, map[0])
        assertEquals(3, updateMapAndReturnNewValue(3,4,map))
        assertEquals(4, map[3])
    }
    @Test
    fun `processing a small range a values`() {
        val map1 = mutableMapOf(0 to 0,3 to 1, 6 to 2)
        val result1 = process(0,3,4, map1)
        assertEquals(3, result1)
    }
    @Test
    fun `processing a large range a values`() {
        val map = mutableMapOf(0 to 0,3 to 1, 6 to 2)
        val result = process(0,3,2018, map)
        assertEquals(436, result)
        val map1 = mutableMapOf(0 to 0,3 to 1, 6 to 2)
        val result1 = process(0,3,30000000 - 2, map1)
        assertEquals(175594, result1)
    }
    @Test
    fun `processing part one using part two process`() {
        val map = mutableMapOf(2 to 0, 1 to 1, 10 to 2, 11 to 3, 0 to 4, 6 to 5 )
        val result = process(0,6,2020 - 2, map)
        assertEquals(232, result)
    }
    @Test
    fun `part two`() {
        val map = mutableMapOf(2 to 0, 1 to 1, 10 to 2, 11 to 3, 0 to 4, 6 to 5 )
        val result = process(0,6,30000000 - 2, map)
        assertEquals(18929178, result)
    }
}