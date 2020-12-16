import com.sun.org.apache.xpath.internal.operations.Bool

fun List<String>.rulesLines() = takeWhile { it.isNotEmpty() }
fun List<String>.yourTicketLine() = dropWhile { it != "your ticket:" }.drop(1).first()
fun List<String>.nearbyTicketLines() = dropWhile{it!= "nearby tickets:"}.drop(1)

fun checkRanges(value:Int, s1:Int,e1:Int, s2:Int, e2:Int ) = value in s1..e1 || value in s2..e2

fun getRangesOnALine(ruleLine:String):List<Int> {
    val rightOfColon = ruleLine.split(": ")[1]
    val textr1 = rightOfColon.split(" or ")[0]
    val textr2 = rightOfColon.split(" or ")[1]
    val s1 = textr1.split("-")[0].toInt()
    val e1 = textr1.split("-")[1].toInt()
    val s2 = textr2.split("-")[0].toInt()
    val e2 = textr2.split("-")[1].toInt()
    return listOf(s1,e1,s2,e2)
}
fun makeRuleForARange(l:List<Int>) =  { value:Int -> checkRanges(value, l[0],l[1],l[2],l[3]) }

fun List<String>.toRules():List<(Int)->Boolean> = rulesLines().map(::getRangesOnALine).map(::makeRuleForARange)

fun List<String>.invalidValues():List<Int> {
    val allNearbyValues = nearbyTicketLines().flatMap{line -> line.split(",").map{ it.toInt() } }
    val rules = toRules()
    val invalidValues = allNearbyValues.fold(listOf<Int>()) {result, value ->
        if (rules.none{it(value)}) result + value
        else result
    }
    return invalidValues
}
//part two
fun String.ticketLineToListOfInts() = split(",").map{ it.toInt() }

fun List<String>.validTickets():List<List<Int>> {
    val rules = toRules()
    return  nearbyTicketLines()
        .map(String::ticketLineToListOfInts)
        .filter{values -> values.allValuesComplyWithRules(rules)}
}
fun List<Int>.allValuesComplyWithRules(rules:List<(Int)->Boolean>) =
    all{value -> rules.any{it(value)}}

fun List<Int>.compliesWith(rule:(Int)->Boolean) = all{rule(it)}

fun potentialRulesForEachCol(data:List<String>):List<List<Int>> {
    val validTickets = data.validTickets()
    val lastCol = validTickets.first().lastIndex
    val rules = data.toRules()
    val valuesInEachColum = (0..lastCol).map{col-> validTickets.map{it[col]}}
    val result = mutableListOf<List<Int>>()
    valuesInEachColum.forEachIndexed { col, values ->
        val rulesForColum = mutableListOf<Int>()
        rules.forEachIndexed { ruleNdx, rule ->
            if (values.compliesWith (rule)) rulesForColum.add(ruleNdx)
        }
        result.add(rulesForColum)
    }
    return result
}
fun List<List<Int>>.rationalise():List<List<Int>>   {
    if (all{it.size == 1}) return this
    val singleRuleCols = filter{it.size == 1}
    return map{rules -> if(rules.size == 1) rules
                        else rules.filter{rule ->
        !singleRuleCols.map{it.first()}.contains(rule)} }.rationalise()
}
fun rulesDecoded(data:List<String>) =
     potentialRulesForEachCol(data).rationalise().flatMap{it}.mapIndexed{index, rulendx ->
         Pair(index, data.rulesLines()[rulendx])}

