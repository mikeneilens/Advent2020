
data class CardNode(val number: Int, var cardBelow: CardNode?)
data class PlayersCards(var topCard: CardNode?, var bottomCard: CardNode?)
data class Game(val hand1: PlayersCards, val hand2: PlayersCards) {

    enum class Winner{ Player1, Player2}

    val previousHands = mutableMapOf<String, Boolean>()

    fun playRoundUntilWinner(count: Int = 1):Winner {
        playRound()
        if (hand1.topCard == null) {println("player 2 wins, count = $count"); return Winner.Player2}
        if (hand2.topCard == null) {println("player 1 wins, count = $count"); return Winner.Player1}
        return playRoundUntilWinner(count + 1)
    }
    fun playRound() {
        val hand1Number = hand1.topCard?.number ?: - 1
        val hand2Number = hand2.topCard?.number ?: - 1
        when (true) {
            hand1Number > hand2Number -> moveCardsHand2ToHand1()
            hand2Number > hand1Number -> moveCardsHand1ToHand2()
            else -> moveCardsToBottom()
        }
    }
    fun playRoundUntilWinner2(count: Int = 1):Winner {
        if (occuredAlready()) return Winner.Player1
        previousHands["$hand1"] = true
        previousHands["$hand2"] = true

        playRound2()
        if (hand1.topCard == null) return Winner.Player2
        if (hand2.topCard == null) return Winner.Player1
        return playRoundUntilWinner2(count + 1)
    }

    fun playRound2() {
        val hand1Number = hand1.topCard?.number ?: - 1
        val hand2Number = hand2.topCard?.number ?: - 1
        when (true) {
            (hand1.topCard!!.number < hand1.topCard!!.count() && hand2.topCard!!.number < hand2.topCard!!.count() ) -> {
                val (hand1Top, hand1Bottom) = hand1.topCard!!.copyCardsBelow()
                val (hand2Top, hand2Bottom) = hand2.topCard!!.copyCardsBelow()
                val subGame = Game(PlayersCards(hand1Top, hand1Bottom), PlayersCards(hand2Top, hand2Bottom) )
                val winner = subGame.playRoundUntilWinner2()
                if (winner == Winner.Player1) moveCardsHand2ToHand1() else moveCardsHand1ToHand2()
            }
            hand1Number > hand2Number -> moveCardsHand2ToHand1()
            hand2Number > hand1Number -> moveCardsHand1ToHand2()
            else -> moveCardsToBottom()
        }
    }
    fun occuredAlready() = (previousHands["$hand1"] != null) || (previousHands["$hand2"] != null)

    fun moveCardsHand2ToHand1() {
        val newTopCardFromHand = hand2.topCard?.cardBelow
        val newTopCardToHand = hand1.topCard?.cardBelow
        hand1.bottomCard?.cardBelow = hand1.topCard
        hand1.bottomCard = hand1.topCard
        hand1.bottomCard?.cardBelow = hand2.topCard
        hand1.bottomCard = hand2.topCard
        hand1.bottomCard?.cardBelow = null
        hand2.topCard = newTopCardFromHand
        hand1.topCard = newTopCardToHand
    }
    fun moveCardsHand1ToHand2() {
        val newTopCardFromHand = hand1.topCard?.cardBelow
        val newTopCardToHand = hand2.topCard?.cardBelow
        hand2.bottomCard?.cardBelow = hand2.topCard
        hand2.bottomCard = hand2.topCard
        hand2.bottomCard?.cardBelow = hand1.topCard
        hand2.bottomCard = hand1.topCard
        hand2.bottomCard?.cardBelow = null
        hand1.topCard = newTopCardFromHand
        hand2.topCard = newTopCardToHand
    }
    fun moveCardsToBottom() {
        val newTopCardHand1 = hand1.topCard?.cardBelow
        val newTopCardHand2 = hand2.topCard?.cardBelow
        hand1.bottomCard?.cardBelow = hand1.topCard
        hand1.bottomCard = hand1.topCard
        hand1.bottomCard?.cardBelow = null
        hand2.bottomCard?.cardBelow = hand2.topCard
        hand2.bottomCard = hand2.topCard
        hand2.bottomCard?.cardBelow = null
        hand1.topCard = newTopCardHand1
        hand2.topCard = newTopCardHand2
    }
}


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

fun List<String>.getHands():Game {
    val cardDecks = parse()
    return Game(cardDecks[0].makeHand(), cardDecks[1].makeHand())
}

fun List<Int>.makeHand():PlayersCards {
    val topCard = CardNode(first(), null)
    var bottomCard = topCard
    drop(1).forEach{ num ->
        val newBottomCard = CardNode(num, null)
        bottomCard.cardBelow = newBottomCard
        bottomCard = newBottomCard
    }
    return PlayersCards(topCard, bottomCard)
}
fun CardNode.count():Int {
    if (cardBelow == null) return 1
    else return 1 + (cardBelow?.count() ?: 1)
}
fun CardNode.calcValue():Long {
    if (cardBelow == null) return count() * number.toLong()
    else return count() * number + (cardBelow?.calcValue() ?: 0)
}

fun CardNode.highestCardBelowTopCard():Int {
    var topCard:CardNode? = this.cardBelow
    var maxNumber = 0
    while (topCard?.cardBelow != null) {
        if (topCard.number > maxNumber) maxNumber = topCard.number
        topCard = topCard.cardBelow
    }
    if (topCard!!.number > maxNumber) maxNumber = topCard!!.number
    return maxNumber
}


fun CardNode.copyCardsBelow():Pair<CardNode,CardNode?> {
    val maxQty = number
    var topCard:CardNode? = this.cardBelow
    var newTopCard = CardNode((topCard?.number ?: -1), null)
    var nextNewCard:CardNode? = newTopCard
    var qtyCopied = 1
    while (topCard?.cardBelow != null && qtyCopied < maxQty) {
        topCard = topCard.cardBelow
        nextNewCard?.cardBelow =  topCard?.let{ CardNode(it.number, null) }
        nextNewCard = nextNewCard?.cardBelow
        qtyCopied++
    }
    return Pair(newTopCard, nextNewCard)
}
