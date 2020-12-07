import com.sun.deploy.security.ruleset.RuleSetParser

fun main() = 0

typealias Bag=String
typealias BagAndRelatedBags=List<String>

typealias Rule = Map<Bag, List<RelatedBag>>
//data class Rule (val bag:Bag, val relatedBags:List<RelatedBag> )
data class RelatedBag (val quantity:Int, val bag:String)

fun toRule(s:String):Pair<Bag,List<RelatedBag>> {
    val bagAndRelatedBags = s.removeSuffix(".").replace("bags","bag").split(" contain ")
    return Pair(bagAndRelatedBags.bag, bagAndRelatedBags.allRelatedBags)
}

val BagAndRelatedBags.bag get() = get(0)
val BagAndRelatedBags.allRelatedBags get() = if (hasNoRelatedBags()) emptyList() else someRelatedBags()
fun BagAndRelatedBags.hasNoRelatedBags() = size < 2 || get(1).startsWith("no")
fun List<String>.someRelatedBags() = if (size > 1) get(1).split(", ").map(::toRelatedBag) else emptyList()

fun toRelatedBag(s:String) = RelatedBag(s.split(" ")[0].toInt(), s.split(" ").drop(1).joinToString(" "))

fun String.parseToRules() = split("\n").map(::toRule).toMap()

fun allBagsContaining(bag:Bag, rules:Rule ,bags:List<Bag> = emptyList()):List<Bag> {
    val parentBags = bagsContaining(bag, rules)
    if (parentBags.isEmpty()) return bags
    else return parentBags + parentBags.flatMap{allBagsContaining(it,rules)}
}

fun bagsContaining(bag:String, rules:Rule) = rules.toList().filter { it.containsBag(bag)}.map{it.first}
fun Pair<Bag, List<RelatedBag>>.containsBag(bag:Bag) = second.map{it.bag}.contains(bag)
fun noOfBagsContaining(bag:Bag, rules:Rule) = allBagsContaining(bag, rules).distinct().size

fun totalBags(bag:Bag, quantity:Int , rules:Rule ):Int {
    val relatedBags = rules[bag]?.let{it } ?: emptyList()
    if (relatedBags.isEmpty()) return quantity
    return quantity + relatedBags.sumBy { relatedBag -> totalBags(relatedBag.bag, quantity * relatedBag.quantity, rules) }
}

