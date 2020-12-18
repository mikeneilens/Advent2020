import kotlin.math.exp

val partOneOperators = listOf(setOf("+","*"))
val partTwoOperators = listOf(setOf("+"),setOf("*"))

val operatorFunction:Map<String, (String, String)->Pair<String,String>> = mapOf(
    "+" to {a, b -> Pair((a.toLong() + b.toLong()).toString(), "$a + $b")},
    "*" to {a, b -> Pair((a.toLong() * b.toLong()).toString(), "$a * $b")}
)

typealias Expression = String

fun evaluate(expression:Expression, operators:Set<String>):String {
    if (expression.contains(operators)) return expression
    val (number1, op, number2) = expression.operatorsAndOperands().firstOrNull(){operators.contains(it.second)} ?: return expression
    val (result, expressionEvaluated) = operatorFunction.getValue(op)(number1, number2)
    return evaluate(expression.replaceFirst(expressionEvaluated,result), operators)
}

fun Expression.firstOperands() = split(" ").windowed(1,2).flatten()
fun Expression.operatorsAnd2ndOperands() = split(" ").drop(1).chunked(2).map{Pair(it[0],it[1])}
fun Expression.operatorsAndOperands() = firstOperands().zip(operatorsAnd2ndOperands()).map{Triple(it.first,it.second.first, it.second.second)}
fun Expression.contains(operators:Set<String>) = (toSet()  union operators).isEmpty()
fun Expression.isCompletelyEvaluated() = !contains(' ')

fun findFirstExpression(expression:Expression):Expression { //the first expression contains inside braces or the full expression if there are no braces.
    var value = ""
    expression.forEach { char ->
        when(char) {
            '(' -> value = ""
            ')' -> return value
            else -> value += char
        }
    }
    return value
}

fun findFirstExpressionAndEvaluate(expression:Expression, operatorSets:List<Set<String>>):Expression {
    val firstExpression = findFirstExpression(expression)
    val result = operatorSets.fold(firstExpression){exp, operatorSet -> evaluate(exp, operatorSet)}
    return if (firstExpression == expression) result
    else expression.replace("($firstExpression)",result)
}

fun evaluateFull(expression:Expression, operatorSets:List<Set<String>> ):Expression = if (expression.isCompletelyEvaluated())  expression
else evaluateFull(findFirstExpressionAndEvaluate(expression,operatorSets),operatorSets)

fun partOne(data:List<String>) =
    data.map {evaluateFull(it, partOneOperators)}.fold(0L){ acc, v -> acc + v.toLong()}

//part two

fun partTwo(data:List<String>) =
    data.map{ evaluateFull(it, partTwoOperators)  }.fold(0L){ acc, v -> acc + v.toLong()}