typealias Bag=String
typealias BagAndRelatedBags=List<String>

typealias Rules = Map<Bag, List<RelatedBag>>
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

//The actual tricky stuff in day07.

fun allBagsContaining(bag:Bag, rules:Rules, bags:List<Bag> = emptyList()):List<Bag> {
    val parentBags = bagsContaining(bag, rules)
    return if (parentBags.isEmpty())
        bags
    else
        parentBags + parentBags.flatMap{allBagsContaining(it,rules)}
}

fun bagsContaining(bag:String, rules:Rules) = rules.toList().filter(bagContains(bag)).map{it.first}
fun bagContains(requiredBag:Bag) = { p:Pair<Bag,List<RelatedBag>> -> p.second.map{it.bag}.contains(requiredBag) }

fun noOfBagsContaining(bag:Bag, rules:Rules) = allBagsContaining(bag, rules).distinct().size

fun totalBags(bag:Bag, qtyOfThisBag:Int, rules:Rules ):Int {
    val relatedBags = rules.getValue(bag)
    return if (relatedBags.isEmpty())
        qtyOfThisBag
    else
        qtyOfThisBag + relatedBags.sumBy { relatedBag -> totalBags(relatedBag.bag, qtyOfThisBag * relatedBag.quantity, rules) }
}

