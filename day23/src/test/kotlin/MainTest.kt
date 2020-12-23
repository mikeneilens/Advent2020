import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = "3,8,9,1,2,5,4,6,7".split(",").map{it.toInt()}
    @Test
    fun `getting first three cups`() {
        assertEquals(listOf(8,9,1), sampleData.pickCups(0))
        assertEquals(listOf(7,3,8), sampleData.pickCups(7))
    }
    @Test
    fun `cycling through indices`() {
        assertEquals(0, cycle(0))
        assertEquals(1, cycle(10))
        assertEquals(9, cycle(-1))
        assertEquals(8, cycle(-2))
    }
    @Test
    fun `finding destination`() {
        assertEquals(2, sampleData.destinationCup(0, sampleData.pickCups(0) ))
        assertEquals(3, listOf(3,2,5,4,6,7,8,9,1).destinationCup(2, listOf(4,6,7)))
        assertEquals(9, listOf(9,2,5,8,4,1,3,6,7).destinationCup(5, listOf(3,6,7)))
    }
    @Test
    fun `doing one round`() {
        val (result, nextStart) = sampleData.processCups(0)
        assertEquals(listOf(3,2,8,9,1,5,4,6,7), result)
        assertEquals(1, nextStart)
        val (result2, nextStart2) = result.processCups(nextStart)
        assertEquals(listOf(3,2,5,4,6,7,8,9,1), result2)
        assertEquals(2, nextStart2)
        val (result3, nextStart3) = result2.processCups(nextStart2)
        assertEquals(listOf(3,4,6,7,2,5,8,9,1), result3)
        assertEquals(6, nextStart3)
    }
    @Test
    fun  `processing data 10 times`() {
        val result = sampleData.process(0)
        assertEquals(listOf(5,8,3,7,4,1,9,2,6),result)
    }
    @Test
    fun `converting list of ints to a string`() {
        assertEquals("92658374", listOf(5,8,3,7,4,1,9,2,6).compacted())
    }
    @Test
    fun  `processing data 100 times`() {
        val result = sampleData.process(0, 100)
        assertEquals("67384529",result.compacted())
    }
    @Test
    fun `part one`() {
        val result = listOf(3,2,6,5,1,9,4,7,8).process(0, 100)
        assertEquals("25368479",result.compacted())
    }

    @Test
    fun `converting list to node and node to list`() {
        val node = listOf(4,8,3).toCup()
        val list = node.toListOfInts()
        assertEquals(listOf(4,8,3), list)
    }

    @Test
    fun `removing three cups from the list`() {
        val list = sampleData.toCup()
        val cups = list.pickupThreeCups()
        assertEquals(listOf(8,9,1), cups.toListOfInts())
        assertEquals(listOf(3,2,5,4,6,7), list.toListOfInts())
    }
    @Test
    fun `whether a list contains a value`() {
        val list = listOf(8,9,1).toCup()
        assertTrue(list.contains(8))
        assertTrue(list.contains(9))
        assertTrue(list.contains(1))
        assertFalse(list.contains(2))
    }
    @Test
    fun `subtracting values from a label`() {
        assertEquals(2, 3.labelMinus(1, 9))
        assertEquals(1, 3.labelMinus(2, 9))
        assertEquals(9, 3.labelMinus(3, 9))
        assertEquals(8, 3.labelMinus(4, 9))
    }
    @Test
    fun `finding the destination label`() {
        val list = sampleData.toCup()
        val threeCups = list.pickupThreeCups()
        assertEquals(2, list.getDestinationLabel(threeCups,9))
    }
    @Test
    fun `finding the node for a label`() {
        val list = sampleData.toCup()
        assertEquals(3,list.getCupForLabel(3).label)
        assertEquals(7,list.getCupForLabel(7).label)
        assertEquals(2,list.getCupForLabel(2).label)
    }
    @Test
    fun `finding the destination node`() {
        val list = sampleData.toCup()
        val threeCups = list.pickupThreeCups()
        assertEquals(2, list.destinationCup(threeCups,9).label)
    }
    @Test
    fun `play round using list`() {
        val list = sampleData.toCup()
        val result = list.playRound()
        assertEquals(listOf(2,8,9,1,5,4,6,7,3), result.toListOfInts())
        val result2 = result.playRound()
        assertEquals(listOf(5,4,6,7,8,9,1,3,2), result2.toListOfInts())
    }
    @Test
    fun `playing ten rounds using the list`() {
        val result = process(sampleData)
        assertEquals("92658374", result.toListOfInts().compacted())
    }
    @Test
    fun `playing 100 rounds using the list`() {
        val result = process(sampleData,100)
        assertEquals("67384529", result.toListOfInts().compacted())
    }
    @Test
    fun `part one using the list`() {
        val result = process(listOf(3,2,6,5,1,9,4,7,8),100)
        assertEquals("25368479", result.toListOfInts().compacted())
    }
    @Test
    fun `part two using list`() {
        val testData  = listOf(3,2,6,5,1,9,4,7,8) + (10..1000000).toList()
        process(testData,10000000)
        val firstCup = mapOfCupsForLabels[1]!!.next
        val secondCup = mapOfCupsForLabels[1]!!.next!!.next
        val answer = (firstCup?.label ?: 0).toLong() * (secondCup?.label ?: 0).toLong()
        assertEquals(44541319250, answer)
    }
 @Test
    fun `part two PJ data using list`() {
        val testData  = listOf( 1,5,7,6,2,3,9,8,4) + (10..1000000).toList()
        process(testData,10000000)
        val firstCup = mapOfCupsForLabels[1]!!.next
        val secondCup = mapOfCupsForLabels[1]!!.next!!.next
        val answer = (firstCup?.label ?: 0).toLong() * (secondCup?.label ?: 0).toLong()
        assertEquals(111057672960, answer)
    }

}

