



fun List<String>.parse():List<List<Int>> {
    val deck1 = mutableListOf<Int>()
    val deck2 = mutableListOf<Int>()
    val cardDecks = listOf(deck1, deck2)
    var deckNo = -1
    filter { it.isNotEmpty()}.forEach{ line->
        if (line.startsWith("Player")) deckNo ++
        else {
            cardDecks[deckNo].add(line.toInt())
        }
    }
    return cardDecks
}

fun List<String>.playGame():Pair<List<Int>,List<Int>> {

    val deck1 = parse()[0].toMutableList()
    val deck2 = parse()[1].toMutableList()

    while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
        if (deck1.first() > deck2.first()) {
            deck1.add(deck1.first())
            deck1.add(deck2.first())
        } else {
            deck2.add(deck2.first())
            deck2.add(deck1.first())
        }
        deck1.removeAt(0)
        deck2.removeAt(0)
    }
    return Pair(deck1, deck2)
}

fun List<String>.playGamePartTwo():Triple<String,List<Int>,List<Int>> {

    val initialDeck1 = parse()[0].toList()
    val initialDeck2 = parse()[1].toList()

    fun process(d1:List<Int>, d2:List<Int>, depth:Int = 0):Triple<String,List<Int>,List<Int>> {
        val history = mutableSetOf<List<Int>>()
        val deck1 = d1.toMutableList()
        val deck2 = d2.toMutableList()

        while (deck1.isNotEmpty() && deck2.isNotEmpty()) {

            if ( history.contains(deck1) || history.contains(deck2) ) {
                return Triple("P1",deck1, deck2)
            }

            history.add(deck1)
            history.add(deck2)

            if (deck1.first() < deck1.size && deck2.first() < deck2.size ) {
                val (winner, _, _) = process(deck1.drop(1).take(deck1.first()), deck2.drop(1).take(deck2.first()), depth + 1)
                if (winner == "P1") {
                    deck1.add(deck1.first())
                    deck1.add(deck2.first())
                } else {
                    deck2.add(deck2.first())
                    deck2.add(deck1.first())
                }
            } else {
                if (deck1.first() > deck2.first()) {
                    deck1.add(deck1.first())
                    deck1.add(deck2.first())
                } else {
                    deck2.add(deck2.first())
                    deck2.add(deck1.first())
                }
            }
            deck1.removeAt(0)
            deck2.removeAt(0)
        }
        return if (deck1.isNotEmpty()) Triple("P1",deck1, deck2)
        else Triple("P2",deck1, deck2)
    }

    return process(initialDeck1, initialDeck2)
}

fun List<Int>.score():Long {
    var i = 0
    var sum = 0L
    while (i <= lastIndex) {
        sum += get(i) * (size - i)
        i++
    }
    return sum
}
