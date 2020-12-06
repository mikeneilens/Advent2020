import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {

    val sampleData = """
        abc

        a
        b
        c

        ab
        ac

        a
        a
        a
        a

        b
    """.trimIndent()
    @Test
    fun `parsing data into groups`() {
        assertEquals(5,sampleData.parseIntoGroups().size)
        val group0 = "abc"
        val group1 = """
            a
            b
            c
        """.trimIndent()
        assertEquals(group0,sampleData.parseIntoGroups()[0])
        assertEquals(group1,sampleData.parseIntoGroups()[1])
    }
    @Test
    fun `parsing into answers`() {
        val group = """
            ab
            ac
        """.trimIndent()
        assertEquals("ab", answersForGroup(group)[0])
        assertEquals("ac", answersForGroup(group)[1])
    }
    @Test
    fun `unique answers in an answers for a group`() {
        val answers1 = listOf("abc")
        val answers2 = listOf("a","b","c")
        val answers3 = listOf("ab","ac")
        val answers4 = listOf("a","a","a","a")
        val answers5 = listOf("a")
        assertEquals(3, uniqueAnswersInGroup(answers1))
        assertEquals(3, uniqueAnswersInGroup(answers2))
        assertEquals(3, uniqueAnswersInGroup(answers3))
        assertEquals(1, uniqueAnswersInGroup(answers4))
        assertEquals(1, uniqueAnswersInGroup(answers5))
    }
    @Test
    fun `some of answers for all groups in sample data`() {
        assertEquals(11, sampleData.uniqueAnswers())
    }
    @Test
    fun`part one`() {
        assertEquals(7120, day06Data.uniqueAnswers())
    }
    @Test
    fun `all people in a group answer 'a' group`() {
        val answers1 = listOf("abc")
        val answers2 = listOf("a","b","c")
        val answers3 = listOf("ab","ac")
        val answers4 = listOf("a","a","a","a")
        val answers5 = listOf("a")
        assertTrue(answers1.allPeopleInTheGroupAnswers('a'))
        assertFalse(answers2.allPeopleInTheGroupAnswers('a'))
        assertTrue(answers3.allPeopleInTheGroupAnswers('a'))
        assertTrue(answers4.allPeopleInTheGroupAnswers('a'))
        assertTrue(answers5.allPeopleInTheGroupAnswers('a'))
    }
    @Test
    fun `questions that everyone in the group answers`() {
        val answers1 = listOf("abc")
        val answers2 = listOf("a","b","c")
        val answers3 = listOf("ab","ac")
        val answers4 = listOf("a","a","a","a")
        val answers5 = listOf("a")
        assertEquals(listOf('a','b','c'), questionsEveryAnswers(answers1))
        assertEquals(listOf<Char>(), questionsEveryAnswers(answers2))
        assertEquals(listOf('a'), questionsEveryAnswers(answers3))
        assertEquals(listOf('a'), questionsEveryAnswers(answers4))
        assertEquals(listOf('a'), questionsEveryAnswers(answers5))
    }
    @Test
    fun `sum of questions everyone in each group answers`() {
        assertEquals(6, sampleData.sumOfQuestionsAnsweredByEachGroup())
    }
    @Test
    fun `part two`() {
        assertEquals(3570, day06Data.sumOfQuestionsAnsweredByEachGroup())
    }
}