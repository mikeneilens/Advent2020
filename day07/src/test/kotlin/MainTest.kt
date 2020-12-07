import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = """
        light red bags contain 1 bright white bag, 2 muted yellow bags.
        dark orange bags contain 3 bright white bags, 4 muted yellow bags.
        bright white bags contain 1 shiny gold bag.
        muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
        shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
        dark olive bags contain 3 faded blue bags, 4 dotted black bags.
        vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
        faded blue bags contain no other bags.
        dotted black bags contain no other bags.
    """.trimIndent()

    @Test
    fun `test parsing a string into a rule containing related bags`() {
        val data = "light red bags contain 1 bright white bag, 2 muted yellow bags."
        val rules = toRule(data)
        assertEquals("light red bag",rules.first)
        assertEquals(listOf(RelatedBag(1,"bright white bag"),RelatedBag(2,"muted yellow bag")),rules.second)

        val data2 = "bright white bags contain 1 shiny gold bag."
        val rules2 = toRule(data2)
        assertEquals("bright white bag", rules2.first)
        assertEquals(listOf( RelatedBag(1,"shiny gold bag") ) , rules2.second)
    }
    @Test
    fun `test parsing a string into a rule containing no related bags`() {
        val data = "faded blue bags contain no other bags."
        val rule = toRule(data)
        assertEquals("faded blue bag", rule.first )
        assertEquals(emptyList<RelatedBag>(), rule.second )
    }
    @Test
    fun `test parsing sampleData into a list of Rules`() {
        val rules = sampleData.parseToRules()
        assertEquals(9, rules.size)
    }
    @Test
    fun `test bag contained in one bag`() {
        val rules = sampleData.parseToRules()
        assertEquals(listOf("shiny gold bag"), bagsContaining("vibrant plum bag", rules))
    }
    @Test
    fun `test all bag containing the bag using sample data`() {
        val rules = sampleData.parseToRules()
        assertEquals(listOf("bright white bag", "muted yellow bag", "light red bag", "dark orange bag"), allBagsContaining("shiny gold bag", rules).distinct())
        assertEquals(4, noOfBagsContaining("shiny gold bag", rules))
    }
    @Test
    fun `part one`() {
        val rules = day07Data.parseToRules()
        assertEquals(272, noOfBagsContaining("shiny gold bag", rules))
    }
    @Test
    fun `test bags inside of gold bag`() {
        val rules = sampleData.parseToRules()
        assertEquals(7, totalBags("dark olive bag",1, rules) - 1)
        assertEquals(11, totalBags("vibrant plum bag",1, rules) - 1)
        println(" --")
        assertEquals(32, totalBags("shiny gold bag",1, rules) - 1)
    }
    @Test
    fun `part two`() {
        val rules = day07Data.parseToRules()
        assertEquals(172247, totalBags("shiny gold bag",1, rules))
    }
}