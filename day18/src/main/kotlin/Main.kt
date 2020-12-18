
fun evaluate(expression:String):String {
    val items = expression.split(" ")
    return items.drop(1).chunked(2).fold(items.first().toLong()) { result, pair ->
        if (pair[0] == "+") result + pair[1].toLong()
        else result * pair[1].toLong()
    }.toString()
}

fun findFirstExpression(expression:String):String {
    var value:String = ""
    expression.forEach { char ->
        when(char) {
            '(' -> value = ""
            ')' -> return value
            else -> value += char
        }
    }
    return value
}
fun findFirstExpressionAndEvaluate(expression:String):String {
    val firstExpression = findFirstExpression(expression)
    val result = evaluate(firstExpression)
    return if (firstExpression == expression) result.toString()
    else expression.replace("($firstExpression)",result)
}
fun evaluateFull(expression:String):String = if (!expression.contains(' '))  expression
    else evaluateFull(findFirstExpressionAndEvaluate(expression))

fun partOne(data:List<String>) =
    data.map(::evaluateFull).fold(0L){ acc,v -> acc + v.toLong()}

//part two
val operatorFunction:Map<String, (String, String)->String> = mapOf(
    "+" to {a, b -> (a.toLong() + b.toLong()).toString()},
    "*" to {a, b -> (a.toLong() * b.toLong()).toString()}
)

fun evaluate(expression:String, operator:String):String {
    if (!expression.contains(operator)) return expression
    val items = expression.split(" ")
    var firstValue = items.first()
    items.drop(1).chunked(2).forEach {
        if (it[0] == operator ) {
            val textExpresion =  "$firstValue $operator ${it[1]}"
            val result = operatorFunction.getValue(operator)(firstValue, it[1])
            return evaluate(expression.replaceFirst(textExpresion,result), operator)
        } else {
            firstValue = it[1]
        }
    }
    return expression
}

fun findFirstExpressionAndEvaluate2(expression:String):String {
    val firstExpression = findFirstExpression(expression)
    val result = evaluate(evaluate(firstExpression,"+") ,"*")
    return if (firstExpression == expression) result.toString()
    else expression.replace("($firstExpression)",result)
}

fun evaluateFull2(expression:String):String = if (!expression.contains(' '))  expression
else evaluateFull2(findFirstExpressionAndEvaluate2(expression))

fun partTwo(data:List<String>) =
    data.map(::evaluateFull2).fold(0L){ acc,v ->
        println("$v");
        acc + v.toLong()}