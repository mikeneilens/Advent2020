import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = """
        class: 1-3 or 5-7
        row: 6-11 or 33-44
        seat: 13-40 or 45-50

        your ticket:
        7,1,14

        nearby tickets:
        7,3,47
        40,4,50
        55,2,20
        38,6,12
    """.trimIndent().split("\n")
    @Test
    fun `getting rulesLines, yourTicketLines and nearByTicketLines from sample data`() {
       val ruleLines = sampleData.rulesLines()
        assertEquals(listOf("class: 1-3 or 5-7", "row: 6-11 or 33-44", "seat: 13-40 or 45-50"), ruleLines)
        val yourTicketLine = sampleData.yourTicketLine()
        assertEquals("7,1,14", yourTicketLine)
        val nearByTicketLines = sampleData.nearbyTicketLines()
        assertEquals(listOf("7,3,47", "40,4,50", "55,2,20", "38,6,12"), nearByTicketLines)
    }
    @Test
    fun `test transforming a rule to a list of ranges`() {
        val result = getRangesOnALine("seat: 13-40 or 45-50")
        assertEquals(listOf(13,40,45,50),result)
    }
    @Test
    fun `changing ranges into a rule`() {
        val rule =  makeRuleForARange(listOf(13,40,45,50))
        assertFalse(rule(12))
        assertTrue(rule(13))
        assertTrue(rule(40))
        assertFalse(rule(41))
        assertTrue(rule(45))
        assertTrue(rule(50))
        assertFalse(rule(51))
    }
    @Test
    fun `create rules for sample data`() {
        val rules = sampleData.toRules()
        assertTrue(rules[2](13))
    }
    @Test
    fun `invalid values on nearyby tickets in sample data is 4,55,12`() {
        assertEquals(listOf(4,55,12), sampleData.invalidValues())
        assertEquals(71, sampleData.invalidValues().sum())
    }
    @Test
    fun `part one`() {
        assertEquals(19070, day16Data.invalidValues().sum())
    }

    val partTwoData = """
        class: 0-1 or 4-19
        row: 0-5 or 8-19
        seat: 0-13 or 16-19

        your ticket:
        11,12,13

        nearby tickets:
        3,9,18
        15,1,5
        5,14,9
    """.trimIndent().split("\n")

    @Test
    fun `all values for a ticket comply with rules`() {
        val rules = sampleData.toRules()
        assertTrue(listOf(7,3,47).allValuesComplyWithRules(rules))
        assertFalse(listOf(40,4,50).allValuesComplyWithRules(rules))
        assertFalse(listOf(55,2,20).allValuesComplyWithRules(rules))
        assertFalse(listOf(38,6,12).allValuesComplyWithRules(rules))
    }
    @Test
    fun `only tickets that comply with the rules`() {
        val rules = sampleData.toRules()
        assertEquals(listOf(listOf(7,3,47)), sampleData.validTickets())
    }
    @Test
    fun `potential rules for each column is (1), (0,1) and (0,1,2)`() {
        assertEquals(listOf(listOf(1),listOf(0,1), listOf(0,1,2)) , potentialRulesForEachCol(partTwoData))
    }
    @Test
    fun `rationalising rules for each col`(){
        val rulesForEachCol = listOf(listOf(1),listOf(0,1), listOf(0,1,2))
        assertEquals(listOf(listOf(1),listOf(0),listOf(2)), rulesForEachCol.rationalise())
    }
    @Test
    fun `decoding rules`() {
        println(rulesDecoded(partTwoData) )
    }
    @Test
    fun `part two`() {
        val decoded = rulesDecoded(day16Data)
        val rulesStartingWithDepart = decoded.filter{it.second.startsWith("depart")}
        val myTicket = day16Data.yourTicketLine().ticketLineToListOfInts()
        val answer = rulesStartingWithDepart.map{ (ndx, ruleDescription) ->
            myTicket[ndx]
        }.fold(1L){acc, v -> acc * v}
        println(answer)
        assertEquals(161926544831, answer)
    }

}