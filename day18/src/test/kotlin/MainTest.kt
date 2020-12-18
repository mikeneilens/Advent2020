import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `test evaluating a simple expression`() {
        assertEquals("71", evaluate("1 + 2 * 3 + 4 * 5 + 6"))
    }
    @Test
    fun `finding first expression between braces`() {
        assertEquals("1 + 2 * 3 + 4 * 5 + 6", findFirstExpression("1 + 2 * 3 + 4 * 5 + 6"))
        assertEquals("2 * 3",findFirstExpression("1 + (2 * 3) + (4 * (5 + 6))"))
    }
    @Test
    fun `find first expression and evaluate it`() {
        assertEquals("71", findFirstExpressionAndEvaluate("1 + 2 * 3 + 4 * 5 + 6"))
        assertEquals("1 + 6 + (4 * (5 + 6))",findFirstExpressionAndEvaluate("1 + (2 * 3) + (4 * (5 + 6))"))
    }
    @Test
    fun `evaluate the whole expression`() {
        assertEquals("71", evaluateFull("1 + 2 * 3 + 4 * 5 + 6"))
        assertEquals("51",evaluateFull("1 + (2 * 3) + (4 * (5 + 6))"))
        assertEquals("26",evaluateFull("2 * 3 + (4 * 5)"))
        assertEquals("437",evaluateFull("5 + (8 * 3 + 9 + 3 * 4 * 3)"))
        assertEquals("12240",evaluateFull("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"))
        assertEquals("13632",evaluateFull("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"))
    }
    @Test
    fun `part one`() {
        assertEquals(86311597203806, partOne(day18Data))
    }


    @Test
    fun `evalaute plus in expression first`() {
        assertEquals("3 * 7 * 11", evaluate("1 + 2 * 3 + 4 * 5 + 6", "+"))
    }
    @Test
    fun `evaluating a simple expresion`() {
        assertEquals("231", findFirstExpressionAndEvaluate2("1 + 2 * 3 + 4 * 5 + 6"))
    }
    @Test
    fun `evaluate the whole expression using part 2 rules`() {
        assertEquals("51",evaluateFull2("1 + (2 * 3) + (4 * (5 + 6))"))
        assertEquals("46",evaluateFull2("2 * 3 + (4 * 5)"))
        assertEquals("1445",evaluateFull2("5 + (8 * 3 + 9 + 3 * 4 * 3)"))
        assertEquals("669060",evaluateFull2("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"))
        assertEquals("23340",evaluateFull2("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"))
    }
    @Test
    fun `part two`() {
        //276894780500845
        //276894767062189
        assertEquals(86311597203806, partTwo(day18Data))
    }
    @Test
    fun `something `() {
//        assertEquals("(7 * 8 * 9 + 57 * 8) * 7 + 8", findFirstExpressionAndEvaluate2("(7 * 8 * 9 + (9 * 6 + 3) * 8) * 7 + 8"))
//        assertEquals("1060 * 7 + 8", findFirstExpressionAndEvaluate2("(7 * 8 * 9 + 57 * 8) * 7 + 8"))
//        assertEquals("7428", findFirstExpressionAndEvaluate2("1060 * 7 + 8"))
        //assertEquals("960", evaluate("7 * 8 * 9 + 57 * 8","*"))
        assertEquals("6728", evaluateFull2("(7 * 8 * 9 + (9 * 6 + 3) * 8) * 7 + 8"))
    }
}