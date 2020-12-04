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
        assertTrue(validByr("1920"))
        assertTrue(validByr("2002"))
        assertFalse(validByr("1919"))
    }
    @Test
    fun `valid iyr`() {
        assertTrue(validIyr("2010"))
        assertTrue(validIyr("2020"))
        assertFalse(validIyr("2021"))
    }
    @Test
    fun `valid eyr`() {
        assertTrue(validEyr("2020"))
        assertTrue(validEyr("2030"))
        assertFalse(validEyr("2031"))
    }
    @Test
    fun `valid hair colours`() {
        assertTrue(validHcl("#123456"))
        assertTrue(validHcl("#456789"))
        assertTrue(validHcl("#abcdef"))
        assertTrue(validHcl("#6b5442"))
        assertFalse(validHcl("##bcdef"))
        assertFalse(validHcl("#Abcdef"))
    }
    @Test
    fun `valid eye colors`() {
        val validColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        for (validColor in validColors) {
            assertTrue(validEcl(validColor))
        }
        assertFalse(validEcl("xyz"))
    }
    @Test
    fun `valid pid`() {
        assertTrue(validPid("012345678"))
        assertFalse(validPid("01234567"))
        assertFalse(validPid("0123x5679"))
        assertFalse(validPid("0123.5679"))
        assertFalse(validPid("-12345679"))
    }
}