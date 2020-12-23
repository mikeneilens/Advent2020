
typealias Position = Int
typealias Label = Int

class Node(var label:Int, var next:Node?=null )

fun List<Int>.toNode():Node {
    val firstNode = Node(first())
    mapOfNodesForLabels[first()] = firstNode
    var prevNode:Node? = firstNode
    drop(1).forEach{
        val nextNode = Node(it)
        mapOfNodesForLabels[it] = nextNode
        prevNode?.next = nextNode
        prevNode = nextNode
    }
    prevNode?.next = firstNode
    return firstNode
}
fun Node.toListOfInts():List<Int> {
    val list = mutableListOf(label)
    var nextNode = next
    while (nextNode != this && nextNode!= null) {
        list.add(nextNode!!.label)
        nextNode = nextNode?.next
    }
    return list
}
fun Node.pickupThreeCups():Node {
    val cup1 = next
    val cup2 = cup1?.next
    val cup3 = cup2?.next
    this.next = cup3?.next
    cup3?.next = null
    return cup1!!
}
tailrec fun Node.contains(otherLabel:Label, firstNode:Node? = null):Boolean {
    if (label == otherLabel ) return true
    if (label == firstNode?.label) return false
    if ( next == null) return false
    return next!!.contains(otherLabel, firstNode ?: this )
}

val mapOfNodesForLabels = mutableMapOf<Int, Node>()

fun Node.destinationNode(threeCups:Node, maxSize:Int = 9, firstNode:Node? = null):Node {
    val destinationLabel = getDestinationLabel(threeCups, maxSize)
    val nodeInMap = mapOfNodesForLabels[destinationLabel]
    if (nodeInMap != null) return nodeInMap
    else {
        val nodeInList = getNodeForLabel(destinationLabel)
        mapOfNodesForLabels[destinationLabel] = nodeInList
        return nodeInList
    }
}

tailrec fun Node.getNodeForLabel(otherLabel:Int, firstNode:Node? = null):Node{
    if (label == otherLabel) return this
    if (this == firstNode) return this
    if ( next == null) return this
    else return next!!.getNodeForLabel(otherLabel, firstNode ?: this )
}

fun Node.getDestinationLabel(threeCups: Node, maxSize: Int) =
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
    if (newLabel > 0) return newLabel else return maxSize + newLabel
}

fun Node.playRound(maxSize:Int = 9):Node {
    val threeCups = pickupThreeCups()
    val destinationCup = destinationNode(threeCups, maxSize)
    threeCups.next!!.next!!.next = destinationCup.next
    destinationCup.next = threeCups
    return this.next!!
}

fun process(data:List<Int>, times:Int = 10):Node {
    val maxSize = data.maxByOrNull { it } ?: 9
    var node = data.toNode()
    (1..times).forEach {
        node = node.playRound(maxSize)
    }
    return node
}