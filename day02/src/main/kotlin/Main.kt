
data class Rule(val v1:Int, val v2:Int, val characther:Char)

data class Line(val rule:Rule, val password:String) {
    val occurencesOfCharacter = password.filter{it == rule.characther}.length

    val isValid = (occurencesOfCharacter >= rule.v1 && occurencesOfCharacter <= rule.v2)

    val isValid2 =
            rule.v1 <= password.length && rule.v2 <= password.length
                && ((password[rule.v1 - 1] == rule.characther) xor (password[rule.v2 - 1] == rule.characther))
}

fun parseDataIntoLine(data: String): Line {
    val splitBySpace = data.split(" ")
    val numbers = splitBySpace[0].split("-").map{it.toInt()}
    val characther = splitBySpace[1].first()
    val password = splitBySpace[2]
    return Line(Rule(numbers[0],numbers[1],characther),password)
}

fun List<Line>.validRules():List<Line> = filter {it.isValid}

fun List<Line>.validRules2():List<Line> = filter {it.isValid2}