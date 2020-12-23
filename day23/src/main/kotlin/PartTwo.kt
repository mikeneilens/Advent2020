

class Cup(val label:Int, var next:Cup?=null )

fun List<Int>.toNode():Cup {
    val firstNode = Cup(first())
    mapOfNodesForLabels[first()] = firstNode
    var prevNode:Cup? = firstNode
    drop(1).forEach{
        val nextNode = Cup(it)
        mapOfNodesForLabels[it] = nextNode
        prevNode?.next = nextNode
        prevNode = nextNode
    }
    prevNode?.next = firstNode
    return firstNode
}
fun Cup.toListOfInts():List<Int> {
    val list = mutableListOf(label)
    var nextNode = next
    while (nextNode != this && nextNode!= null) {
        list.add(nextNode.label)
        nextNode = nextNode.next
    }
    return list
}
fun Cup.pickupThreeCups():Cup {
    val cup1 = next
    val cup2 = cup1?.next
    val cup3 = cup2?.next
    this.next = cup3?.next
    cup3?.next = null
    return cup1!!
}
tailrec fun Cup.contains(otherLabel:Int, firstNode:Cup? = null):Boolean {
    if (label == otherLabel ) return true
    if (label == firstNode?.label) return false
    if ( next == null) return false
    return next!!.contains(otherLabel, firstNode ?: this )
}

val mapOfNodesForLabels = mutableMapOf<Int, Cup>()

fun Cup.destinationNode(threeCups:Cup, maxSize:Int = 9):Cup {
    val destinationLabel = getDestinationLabel(threeCups, maxSize)
    val nodeInMap = mapOfNodesForLabels[destinationLabel]
    return if (nodeInMap != null) nodeInMap
    else {
        val nodeInList = getNodeForLabel(destinationLabel)
        mapOfNodesForLabels[destinationLabel] = nodeInList
        nodeInList
    }
}

tailrec fun Cup.getNodeForLabel(otherLabel:Int, firstNode:Cup? = null):Cup{
    if (label == otherLabel) return this
    if (this == firstNode) return this
    return if ( next == null) this
    else next!!.getNodeForLabel(otherLabel, firstNode ?: this )
}

fun Cup.getDestinationLabel(threeCups: Cup, maxSize: Int) =
    if (!threeCups.contains(label.labelMinus(1, maxSize)))
        label.labelMinus(1, maxSize)
    else if (!threeCups.contains(label.labelMinus(2, maxSize)))
        label.labelMinus(2, maxSize)
    else if (!threeCups.contains(label.labelMinus(3, maxSize)))
        label.labelMinus(3, maxSize)
    else
        label.labelMinus(4, maxSize)


fun Int.labelMinus(value:Int, maxSize:Int):Int {
    val newLabel = this - value
    return if (newLabel > 0) newLabel else maxSize + newLabel
}

fun Cup.playRound(maxSize:Int = 9):Cup {
    val threeCups = pickupThreeCups()
    val destinationCup = destinationNode(threeCups, maxSize)
    threeCups.next!!.next!!.next = destinationCup.next
    destinationCup.next = threeCups
    return this.next!!
}

fun process(data:List<Int>, times:Int = 10):Cup {
    val maxSize = data.maxByOrNull { it } ?: 9
    var node = data.toNode()
    (1..times).forEach { _ ->
        node = node.playRound(maxSize)
    }
    return node
}