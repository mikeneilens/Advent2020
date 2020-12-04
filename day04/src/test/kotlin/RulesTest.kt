import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RulesTest {
    @Test
    fun `string 2015 is in range 2010 to 2020`() {
        assertTrue("2015".inRange(2010, 2020))
    }
    @Test
    fun `string 2010 and 2020 is in range 2010 to 2020`() {
        assertTrue("2010".inRange(2010, 2020))
        assertTrue("2020".inRange(2010, 2020))
    }
    @Test
    fun `string 2009 and 2021 is not in range 2010 to 2020`() {
        assertFalse("2009".inRange(2010, 2020))
        assertFalse("2021".inRange(2010, 2020))
    }
    @Test
    fun `string xyz is not in range 2010 to 2020`() {
        assertFalse("2009".inRange(2010, 2020))
        assertFalse("2021".inRange(2010, 2020))
    }

    @Test
    fun `valid byr`() {
        assertTrue(byrRule("1920"))
        assertTrue(byrRule("2002"))
        assertFalse(byrRule("1919"))
    }
    @Test
    fun `valid iyr`() {
        assertTrue(iyrRule("2010"))
        assertTrue(iyrRule("2020"))
        assertFalse(iyrRule("2021"))
    }
    @Test
    fun `valid eyr`() {
        assertTrue(eyrRule("2020"))
        assertTrue(eyrRule("2030"))
        assertFalse(eyrRule("2031"))
    }
    @Test
    fun `valid hair colours`() {
        assertTrue(hclRule("#123456"))
        assertTrue(hclRule("#456789"))
        assertTrue(hclRule("#abcdef"))
        assertTrue(hclRule("#6b5442"))
        assertFalse(hclRule("##bcdef"))
        assertFalse(hclRule("#Abcdef"))
    }
    @Test
    fun `valid eye colors`() {
        val validColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        for (validColor in validColors) {
            assertTrue(eclRule(validColor))
        }
        assertFalse(eclRule("xyz"))
    }
    @Test
    fun `valid pid`() {
        assertTrue(pidRule("012345678"))
        assertFalse(pidRule("01234567"))
        assertFalse(pidRule("0123x5679"))
        assertFalse(pidRule("0123.5679"))
        assertFalse(pidRule("-12345679"))
    }
}