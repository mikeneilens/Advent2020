val partOneOperators = listOf(setOf("+","*"))
val partTwoOperators = listOf(setOf("+"),setOf("*"))

val operatorFunction:Map<String, (String, String)->String> = mapOf(
    "+" to {a, b -> (a.toLong() + b.toLong()).toString()},
    "*" to {a, b -> (a.toLong() * b.toLong()).toString()}
)

fun evaluate(expression:String, operators:Set<String>):String {
    if ((expression.toSet() union operators).isEmpty()) return expression
    val items = expression.split(" ")
    var firstValue = items.first()
    items.drop(1).chunked(2).forEach {
        val operator = operators.firstOrNull{operator -> it[0] == operator}
        if (operator != null ) {
            val result = operatorFunction.getValue(operator)(firstValue, it[1])
            return evaluate(expression.replaceFirst("$firstValue $operator ${it[1]}",result), operators)
        } else {
            firstValue = it[1]
        }
    }
    return expression
}

fun findFirstExpression(expression:String):String {
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

fun findFirstExpressionAndEvaluate(expression:String, operators:List<Set<String>>):String {
    val firstExpression = findFirstExpression(expression)
    var result = firstExpression
    operators.forEach { operatorSet -> result = evaluate(result,operatorSet)   }
    return if (firstExpression == expression) result
    else expression.replace("($firstExpression)",result)
}

fun evaluateFull(expression:String, operatorSets:List<Set<String>> ):String = if (!expression.contains(' '))  expression
else evaluateFull(findFirstExpressionAndEvaluate(expression,operatorSets),operatorSets)

fun partOne(data:List<String>) =
    data.map {evaluateFull(it, partOneOperators)}.fold(0L){ acc, v -> acc + v.toLong()}

//part two

fun partTwo(data:List<String>) =
    data.map{ evaluateFull(it, partTwoOperators)  }.fold(0L){ acc, v -> acc + v.toLong()}