import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = """
        ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
        byr:1937 iyr:2017 cid:147 hgt:183cm

        iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
        hcl:#cfa07d byr:1929

        hcl:#ae17e1 iyr:2013
        eyr:2024
        ecl:brn pid:760753108 byr:1931
        hgt:179cm

        hcl:#cfa07d eyr:2025 pid:166559648
        iyr:2011 ecl:brn hgt:59in
    """.trimIndent()
    @Test
    fun `spliting data into separate passports creates 4 potential passports`() {
        assertEquals(4, sampleData.toPassports().size)
        val expectedPassport0 = """
            ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
            byr:1937 iyr:2017 cid:147 hgt:183cm
        """.trimIndent()
        val expectedPassport3 = """
            hcl:#cfa07d eyr:2025 pid:166559648
            iyr:2011 ecl:brn hgt:59in
        """.trimIndent()
        assertEquals(4, sampleData.toPassports().size)
        assertEquals(expectedPassport0, sampleData.toPassports()[0])
        assertEquals(expectedPassport3, sampleData.toPassports()[3])
    }
    @Test
    fun `a Pair is created correcty when a string contains key and value separated by a colon`(){
        assertEquals(Pair("key123","abcd"), "key123:abcd".toKeyValuePair())
    }
    @Test
    fun `a map containing correct key valeus is created with a passport when credentials are separated by spaces`(){
        val passport = "iyr:2013 ecl:amb cid:350 eyr:2023"
        val credentials = passport.toCredentials()
        assertEquals(4,credentials.size)
        assertEquals("2013", credentials["iyr"])
        assertEquals("amb", credentials["ecl"])
        assertEquals("350", credentials["cid"])
        assertEquals("2023", credentials["eyr"])
    }
    @Test
    fun `a map containing correct key valeus is created with a passport when credentials are separated by spaces and new lines`(){
        val passport = """
            ecl:gry hcl:#fffffd
            byr:1937 hgt:183cm
        """.trimIndent()

        val credentials = passport.toCredentials()
        assertEquals(4,credentials.size)
        assertEquals("gry", credentials["ecl"])
        assertEquals("#fffffd", credentials["hcl"])
        assertEquals("1937", credentials["byr"])
        assertEquals("183cm", credentials["hgt"])
    }

    @Test
    fun `credentials are a valid passport if they contain all of the keys needed`() {
        val credentials = mapOf<String,String>("ecl" to "gry","pid" to "860033327", "eyr" to "2020", "hcl" to "#fffffd",
        "byr" to "1937", "iyr" to "2017", "cid" to "147", "hgt" to "183cm")
        assertTrue(credentials.passportFieldsArePresent())
    }
    @Test
    fun `credentials are not a valid passport if they miss one of the keys needed`() {
        val credentials = mapOf<String,String>("ecl" to "gry","pid" to "860033327", "eyr" to "2020", "hcl" to "#fffffd",
            "byr" to "1937", "iyr" to "2017", "hgt" to "183cm")
        assertFalse(credentials.passportFieldsArePresent())
    }
    @Test
    fun `credentials are valid north pole credentials if they contain all of the passport credentials except cid`() {
        val credentials = mapOf<String,String>("ecl" to "gry","pid" to "860033327", "eyr" to "2020", "hcl" to "#fffffd",
            "byr" to "1937", "iyr" to "2017", "hgt" to "183cm")
        assertTrue(credentials.northPoleCredentialsFieldsArePresent())
    }
    @Test
    fun `credentials are not valid north pole credentials if they dont contain all of the passport credentials`() {
        val credentials = mapOf<String,String>("ecl" to "gry","pid" to "860033327", "eyr" to "2020", "hcl" to "#fffffd",
            "byr" to "1937", "iyr" to "2017")
        assertFalse(credentials.northPoleCredentialsFieldsArePresent())
    }
    @Test
    fun `sample data should contain 2 valid passports`() {
        assertEquals(2, sampleData.passportsContainingRequiredFields().size)

        val firstPassportCredentials = sampleData.toPassports()[0].toCredentials()
        val thirdPassportCredentials = sampleData.toPassports()[2].toCredentials()

        assertEquals(firstPassportCredentials, sampleData.passportsContainingRequiredFields()[0])
        assertEquals(thirdPassportCredentials, sampleData.passportsContainingRequiredFields()[1])
    }
    @Test
    fun `part one`() {
        assertEquals(196, dayFourData.passportsContainingRequiredFields().size)
    }

    @Test
    fun `passports that dont pass rules`() {
        val validPassports = """
            eyr:1972 cid:100
            hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

            iyr:2019
            hcl:#602927 eyr:1967 hgt:170cm
            ecl:grn pid:012533040 byr:1946

            hcl:dab227 iyr:2012
            ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

            hgt:59cm ecl:zzz
            eyr:2038 hcl:74454a iyr:2023
            pid:3556412378 byr:2007
        """.trimIndent()

        val credentialsForEachPassport:List<Credentials> = validPassports.toPassports().map{it.toCredentials()}
        for (credentials in credentialsForEachPassport) {
           assertFalse(credentials.allValid())
        }
    }
    @Test
    fun `passports that pass rules`() {
        val validPassports = """
            pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
            hcl:#623a2f

            eyr:2029 ecl:blu cid:129 byr:1989
            iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

            hcl:#888785
            hgt:164cm byr:2001 iyr:2015 cid:88
            pid:545766238 ecl:hzl
            eyr:2022

            iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719
        """.trimIndent()

        val credentialsForEachPassport:List<Credentials> = validPassports.toPassports().map{it.toCredentials()}
        for (credentials in credentialsForEachPassport) {
            assertTrue(credentials.allValid())
        }
    }
    @Test
    fun `passports that are valid and pass the rules`() {
        val passports = """
            pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
            hcl:#623a2f

            eyr:2029 ecl:blu cid:129 byr:1989
            iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm
            
            hcl:dab227 iyr:2012
            ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

            hcl:#888785
            hgt:164cm byr:2001 iyr:2015 cid:88
            pid:545766238 ecl:hzl
            eyr:2022

            iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719
        """.trimIndent()

        assertEquals(4, passports.validPassports().size)
    }
    @Test
    fun `part two`() {
        assertEquals(114, dayFourData.validPassports().size)
    }
}