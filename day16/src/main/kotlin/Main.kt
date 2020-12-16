
fun List<String>.rulesLines() = takeWhile { it.isNotEmpty() }
fun List<String>.yourTicketLine() = dropWhile { it != "your ticket:" }.drop(1).first()
fun List<String>.nearbyTicketLines() = dropWhile{it!= "nearby tickets:"}.drop(1)

fun checkRanges(value:Int, s1:Int,e1:Int, s2:Int, e2:Int ) = value in s1..e1 || value in s2..e2

fun getRangesOnALine(ruleLine:String):List<Int> {
    val rightOfColon = ruleLine.split(": ")[1]
    val textR1 = rightOfColon.split(" or ")[0]
    val textR2 = rightOfColon.split(" or ")[1]
    return listOf(
        textR1.split("-")[0].toInt(),
        textR1.split("-")[1].toInt(),
        textR2.split("-")[0].toInt(),
        textR2.split("-")[1].toInt(),
    )
}
fun makeRuleForARange(l:List<Int>) =  { value:Int -> checkRanges(value, l[0],l[1],l[2],l[3]) }

fun List<String>.getRules():List<(Int)->Boolean> = rulesLines().map(::getRangesOnALine).map(::makeRuleForARange)

fun List<String>.invalidValues():List<Int> {
    val allNearbyValues = nearbyTicketLines().flatMap{line -> line.split(",").map{ it.toInt() } }
    val rules = getRules()
    return allNearbyValues.fold(listOf()) { result, value ->
        if (rules.none {it(value)}) result + value
        else result
    }
}
//part two
fun String.ticketLineToListOfInts() = split(",").map{ it.toInt() }

fun List<String>.validTickets():List<List<Int>> {
    val rules = getRules()
    return  nearbyTicketLines()
        .map(String::ticketLineToListOfInts)
        .filter{values -> values.allValuesComplyWithRules(rules)}
}

fun List<Int>.allValuesComplyWithRules(rules:List<(Int)->Boolean>) =
    all{value -> rules.any{it(value)}}

fun List<Int>.compliesWith(rule:(Int)->Boolean) = all{rule(it)}

fun List<String>.potentialRulesForEachCol():List<List<Int>> {
    val validTickets = validTickets()
    val maximumCol = validTickets.first().lastIndex
    val rules = getRules()
    val valuesInEachColumn = (0..maximumCol).map{ col-> validTickets.map{it[col]}}
    val result = mutableListOf<List<Int>>()
    valuesInEachColumn.forEach { values ->
        result.add(values.rulesThatWorkForEveryValue(rules))
    }
    return result
}

fun List<Int>.rulesThatWorkForEveryValue(rules:List<(Int)->Boolean>):List<Int> {
    val rulesForColumn = mutableListOf<Int>()
    rules.forEachIndexed { ruleNdx, rule ->
        if (this.compliesWith(rule)) rulesForColumn.add(ruleNdx)
    }
    return rulesForColumn
}

fun List<List<Int>>.rationalise():List<List<Int>>   {
    if (all{it.size == 1}) return this
    val columnsWithOneRule = filter{it.size == 1}.map{it.first()}
    return map{ruleIndexes -> if(ruleIndexes.size == 1) ruleIndexes
                        else ruleIndexes.filter{ruleIndex ->
        !columnsWithOneRule.contains(ruleIndex)} }.rationalise()
}
fun List<String>.ruleIndexAndNameForEachColumn() =
     potentialRulesForEachCol().rationalise().flatten().mapIndexed{ columnIndex, ruleNdx ->
         Pair(columnIndex, rulesLines()[ruleNdx])}

fun partTwo(data:List<String>): List<Int> {
    val columnsWithRuleNamesStartingWithDepart
            = data.ruleIndexAndNameForEachColumn()
                  .filter (::nameStartsWithDepart)
                  .map { it.first }
    val yourTicket = data.yourTicketLine().ticketLineToListOfInts()
    return columnsWithRuleNamesStartingWithDepart.map { columnIndex ->
        yourTicket[columnIndex]
    }
}
fun nameStartsWithDepart(p:Pair<Int, String>):Boolean = p.second.startsWith("depart")