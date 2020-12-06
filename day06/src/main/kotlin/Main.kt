
fun String.parseIntoGroups(): List<String> = split("\n\n")
fun answersForGroup(group:String): List<String> = group.split("\n")

fun uniqueAnswersInGroup(answers:List<String>):Int = answers.concatonated().toSet().size
fun List<String>.concatonated() = joinToString("")

fun String.uniqueAnswers() = parseIntoGroups()
    .map(::answersForGroup)
    .sumBy(::uniqueAnswersInGroup)

fun questionsEveryOneAnswers(groupAnswers:List<String>) =
    ('a'..'z').filter{ groupAnswers.allPeopleInTheGroupAnswers(it) }

fun List<String>.allPeopleInTheGroupAnswers(a:Char) = count{it.contains(a)} == size

fun String.sumOfQuestionsAnsweredByEachGroup() = parseIntoGroups()
    .map(::answersForGroup)
    .map(::questionsEveryOneAnswers)
    .sumBy { it.count() }