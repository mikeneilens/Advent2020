
fun String.parseIntoGroups(): List<String> = split("\n\n")
fun answersForGroup(group:String): List<String> = group.split("\n")

fun uniqueAnswersInGroup(answers:List<String>):Int = answers.concatonated().toSet().size
fun List<String>.concatonated() = joinToString("")

fun String.uniqueAnswers() = parseIntoGroups()
    .map(::answersForGroup)
    .map(::uniqueAnswersInGroup)
    .sum()

fun questionsEveryAnswers(groupAnswers:List<String>) =
    ('a'..'z').filter{ groupAnswers.allPeopleInTheGroupAnswers(it) }

fun String.sumOfQuestionsAnsweredByEachGroup() = parseIntoGroups()
    .map(::answersForGroup)
    .map(::questionsEveryAnswers)
    .map { it.count() }
    .sum()

fun List<String>.allPeopleInTheGroupAnswers(a:Char) = count{it.contains(a)} == size
