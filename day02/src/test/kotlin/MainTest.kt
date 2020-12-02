import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {

    val sampleData = "1-3 a: abcde\n" +
            "1-3 b: cdefg\n" +
            "2-9 c: ccccccccc"

    @Test
    fun `parse data containing a rule and password into a Line`() {
        val data = "1-3 a: abcde"
        val expectedResult = Line(Rule(1,3,'a'),"abcde")
        assertEquals(expectedResult,parseDataIntoLine(data))
    }
    @Test
    fun `occurencesOfCharacter is calculated correctly for a line containing 0 characters in the rule`() {
        val rule = Line(Rule(1,3,'z'),"abcde")
        assertEquals(0, rule.occurencesOfCharacter)
    }
    @Test
    fun `occurencesOfCharacter is calculated correctly for a line containing 1 characters in the rule`() {
        val rule = Line(Rule(1,3,'c'),"abcde")
        assertEquals(1, rule.occurencesOfCharacter)
    }
    @Test
    fun `occurencesOfCharacter is calculated correctly for a line containing3 characters in the rule`() {
        val rule = Line(Rule(1,3,'a'),"abcaade")
        assertEquals(3, rule.occurencesOfCharacter)
    }
    @Test
    fun `line is not valid when occurences of a letter is less than the minimum`() {
        val rule = Line(Rule(1,3,'z'),"abcaade")
        assertFalse(rule.isValid)
    }
    @Test
    fun `line is not valid when occurences of a letter is more than the maximum`() {
        val rule = Line(Rule(1,2,'a'),"abcaade")
        assertFalse(rule.isValid)
    }
    @Test
    fun `line is valid when occurences of a letter is between min and max`() {
        val rule = Line(Rule(1,3,'a'),"abcaade")
        assertTrue(rule.isValid)
    }
    @Test
    fun `only valid lines are returned by validRules from the sample data`() {
        val noOfValidLines = sampleData.split("\n").map(::parseDataIntoLine).validRules().size
        assertEquals(2,noOfValidLines)
    }
    @Test
    fun `day02 part 1`() {
        val noOfValidLines = day2Data.split("\n").map(::parseDataIntoLine).validRules().size
        println("no of valid passwords is $noOfValidLines")
    }
    @Test
    fun `isValid2 returns false when rule v1 is larger than password length`() {
        val rule = Line(Rule(6,3,'c'),"abcde")
        assertFalse(rule.isValid2)
    }
    @Test
    fun `isValid2 returns false when rule v2 is larger than password length`() {
        val rule = Line(Rule(1,6,'c'),"abcde")
        assertFalse(rule.isValid2)
    }
    @Test
    fun `isValid2 returns true when character in password at position of rule v1 matches rule character`() {
        val rule = Line(Rule(1,3,'a'),"abcde")
        assertTrue(rule.isValid2)
    }
    @Test
    fun `isValid2 returns true when character in password at position of rule v2 matches rule character`() {
        val rule = Line(Rule(1,3,'c'),"abcde")
        assertTrue(rule.isValid2)
    }
    @Test
    fun `isValid2 returns false when characters in password at position of rule v1 and v2 matches rule character`() {
        val rule = Line(Rule(1,3,'c'),"abade")
        assertFalse(rule.isValid2)
    }
    @Test
    fun `only valid lines are returned by validRules2 from the sample data`() {
        val noOfValidLines = sampleData.split("\n").map(::parseDataIntoLine).validRules2().size
        assertEquals(1,noOfValidLines)
    }
    @Test
    fun `day02 part 2`() {
        val noOfValidLines = day2Data.split("\n").map(::parseDataIntoLine).validRules2().size
        println("no of valid passwords is $noOfValidLines")
    }
}