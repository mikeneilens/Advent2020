import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `test evaluating a simple expression`() {
        assertEquals("71", "1 + 2 * 3 + 4 * 5 + 6".evaluate(partOneOperators[0]))
    }
    @Test
    fun `finding first expression between braces`() {
        assertEquals("1 + 2 * 3 + 4 * 5 + 6", findFirstExpression("1 + 2 * 3 + 4 * 5 + 6"))
        assertEquals("2 * 3",findFirstExpression("1 + (2 * 3) + (4 * (5 + 6))"))
    }
    @Test
    fun `find first expression and evaluate it`() {
        assertEquals("71", findFirstExpressionAndEvaluate("1 + 2 * 3 + 4 * 5 + 6",partOneOperators))
        assertEquals("1 + 6 + (4 * (5 + 6))",findFirstExpressionAndEvaluate("1 + (2 * 3) + (4 * (5 + 6))",partOneOperators))
    }
    @Test
    fun `evaluate the whole expression`() {
        assertEquals("71", evaluateFull("1 + 2 * 3 + 4 * 5 + 6",partOneOperators))
        assertEquals("51", evaluateFull("1 + (2 * 3) + (4 * (5 + 6))",partOneOperators))
        assertEquals("26", evaluateFull("2 * 3 + (4 * 5)",partOneOperators))
        assertEquals("437", evaluateFull("5 + (8 * 3 + 9 + 3 * 4 * 3)",partOneOperators))
        assertEquals("12240", evaluateFull("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))",partOneOperators))
        assertEquals("13632", evaluateFull("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2",partOneOperators))
    }
    @Test
    fun `part one`() {
        assertEquals(86311597203806, partOne(day18Data))
    }


    @Test
    fun `evalaute plus in expression first`() {
        assertEquals("3 * 7 * 11", "1 + 2 * 3 + 4 * 5 + 6".evaluate(setOf("+")))
    }
    @Test
    fun `evaluating a simple expresion`() {
        assertEquals("231", findFirstExpressionAndEvaluate("1 + 2 * 3 + 4 * 5 + 6",partTwoOperators))
    }
    @Test
    fun `evaluate the whole expression using part 2 rules`() {
        assertEquals("51", evaluateFull("1 + (2 * 3) + (4 * (5 + 6))",partTwoOperators))
        assertEquals("46", evaluateFull("2 * 3 + (4 * 5)",partTwoOperators))
        assertEquals("1445", evaluateFull("5 + (8 * 3 + 9 + 3 * 4 * 3)",partTwoOperators))
        assertEquals("669060", evaluateFull("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))",partTwoOperators))
        assertEquals("23340", evaluateFull("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2",partTwoOperators))
    }
    @Test
    fun `part two`() {
        assertEquals(276894767062189, partTwo(day18Data))
    }
}