
fun partOne(puzzleInput:String):Int {
    val (ruleMap, messages) = parseInput(puzzleInput)
    val regexRule0 = expandRule(ruleMap, "0")
    return messages.count { message -> regexRule0.matches(message) }
}

fun partTwo(puzzleInput: String):Int {
    val (ruleMap, messages) = parseInput(puzzleInput)

    val rule42 = expandRule(ruleMap, "42").toString()
    val rule31 = expandRule(ruleMap, "31").toString()

    return messages.count { testMessageInPart2(it, rule42, rule31, 20) }
}


fun parseInput(input: String): Pair<Map<String, String>, List<String>> {
    val (rules, messages) = input.trim().split("\n\n")

    val ruleMap = rules.split("\n")
        .map { it.split(": ") }
        .map { (id, rule) -> id to rule }
        .toMap()

    return Pair(ruleMap, messages.split("\n"))
}

// recursive rule substitutions:
// rule 8 becomes: 42 | 42 8
// - matches one more more applications of rule 42
// rule 11 becomes: 42 31 | 42 11 41
// - matches rule 42 n times, then rule 32 n times, for some n > 0
//
// and rule 0: 8 11
// hence:
fun testMessageInPart2(message: String, rule42: String, rule31: String, maxN: Int) =
    (1..maxN).any { i ->
        val rule0 = """($rule42)+($rule42){$i}($rule31){$i}""".toRegex()
        rule0.matches(message)
    }

fun expandRule(ruleMap: Map<String, String>, ruleId: String): Regex {
    var rule = ruleMap[ruleId] ?: ""
    do {
        rule = rule.expandRuleOneLevel(ruleMap)
    } while (rule.containsNumber())

    return rule.replace(" ", "").replace("\"", "").toRegex()
}

fun String.expandRuleOneLevel(ruleMap: Map<String, String>): String =
    this
        .split(" ").joinToString(" ") {
            if (it.isNumber()) {
                val replacementRule = ruleMap[it] ?: ""
                if (replacementRule.contains("|")) "( $replacementRule )"
                else replacementRule
            } else it
        }

private val numberRegex = """\d+""".toRegex()
private fun String.containsNumber() = numberRegex.containsMatchIn(this)
private fun String.isNumber() = numberRegex.matches(this)

val example2 = """42: 9 14 | 10 1
9: 14 27 | 1 26
10: 23 14 | 28 1
1: "a"
11: 42 31
5: 1 14 | 15 1
19: 14 1 | 14 14
12: 24 14 | 19 1
16: 15 1 | 14 14
31: 14 17 | 1 13
6: 14 14 | 1 14
2: 1 24 | 14 4
0: 8 11
13: 14 3 | 1 12
15: 1 | 14
17: 14 2 | 1 7
23: 25 1 | 22 14
28: 16 1
4: 1 1
20: 14 14 | 1 15
3: 5 14 | 16 1
27: 1 6 | 14 18
14: "b"
21: 14 1 | 1 14
25: 1 1 | 1 14
22: 14 14
8: 42
26: 14 22 | 1 20
18: 15 15
7: 14 5 | 1 21
24: 14 1
abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
bbabbbbaabaabba
babbbbaabbbbbabbbbbbaabaaabaaa
aaabbbbbbaaaabaababaabababbabaaabbababababaaa
bbbbbbbaaaabbbbaaabbabaaa
bbbababbbbaaaaaaaabbababaaababaabab
ababaaaaaabaaab
ababaaaaabbbaba
baabbaaaabbaaaababbaababb
abbbbabbbbaaaababbbbbbaaaababb
aaaaabbaabaaaaababaa
aaaabbaaaabbaaa
aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
babaaabbbaaabaababbaabababaaab
aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba
"""