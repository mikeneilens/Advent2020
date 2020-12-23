
val mapOfCupsForLabels = mutableMapOf<Int, Cup>()

class Cup(val label:Int, var next:Cup?=null ) {

    fun toListOfInts():List<Int> {
        val list = mutableListOf(label)
        var nextCup = next
        while (nextCup != this && nextCup!= null) {
            list.add(nextCup.label)
            nextCup = nextCup.next
        }
        return list
    }

    fun pickupThreeCups():Cup {
        val cup1 = next
        val cup2 = cup1?.next
        val cup3 = cup2?.next
        this.next = cup3?.next
        cup3?.next = null
        return cup1!!
    }

    fun contains(otherLabel:Int, firstCup:Cup? = null):Boolean {
        if (label == otherLabel ) return true
        if (label == firstCup?.label) return false
        if ( next == null) return false
        return next!!.contains(otherLabel, firstCup ?: this )
    }

    fun destinationCup(threeCups:Cup, maxSize:Int = 9):Cup {
        val destinationLabel = getDestinationLabel(threeCups, maxSize)
        val cupInMap = mapOfCupsForLabels[destinationLabel]
        return if (cupInMap != null) cupInMap
        else {
            val cupInList = getCupForLabel(destinationLabel)
            mapOfCupsForLabels[destinationLabel] = cupInList
            cupInList
        }
    }

    fun getDestinationLabel(threeCups: Cup, maxSize: Int) =
        if (!threeCups.contains(label.labelMinus(1, maxSize)))
            label.labelMinus(1, maxSize)
        else if (!threeCups.contains(label.labelMinus(2, maxSize)))
            label.labelMinus(2, maxSize)
        else if (!threeCups.contains(label.labelMinus(3, maxSize)))
            label.labelMinus(3, maxSize)
        else
            label.labelMinus(4, maxSize)

    fun playRound(maxSize:Int = 9):Cup {
        val threeCups = pickupThreeCups()
        val destinationCup = destinationCup(threeCups, maxSize)
        threeCups.next!!.next!!.next = destinationCup.next
        destinationCup.next = threeCups
        return this.next!!
    }
}

fun List<Int>.toCup():Cup {
    val firstCup = Cup(first())
    mapOfCupsForLabels[first()] = firstCup
    var prevCup:Cup? = firstCup
    drop(1).forEach{
        val nextCup = Cup(it)
        mapOfCupsForLabels[it] = nextCup
        prevCup?.next = nextCup
        prevCup = nextCup
    }
    prevCup?.next = firstCup
    return firstCup
}

tailrec fun Cup.getCupForLabel(otherLabel:Int, firstCup:Cup? = null):Cup{
    if (label == otherLabel) return this
    if (this == firstCup) return this
    return if ( next == null) this
    else next!!.getCupForLabel(otherLabel, firstCup ?: this )
}

fun Int.labelMinus(value:Int, maxSize:Int):Int {
    val newLabel = this - value
    return if (newLabel > 0) newLabel else maxSize + newLabel
}

fun process(data:List<Int>, times:Int = 10):Cup {
    val maxSize = data.maxByOrNull { it } ?: 9
    var cup = data.toCup()
    (1..times).forEach { _ ->
        cup = cup.playRound(maxSize)
    }
    return cup
}