import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {
    private val sampleData = """
        Player 1:
        9
        2
        6
        3
        1

        Player 2:
        5
        8
        4
        7
        10
    """.trimIndent().split("\n")

    @Test
    fun `convert a list of ints to a card`() {
        val (topCard, bottomCard) =  listOf(9,2,6,3,1).makeHand()
        assertEquals(9, topCard?.number)
        assertEquals(1, bottomCard?.number)
        assertEquals(5, topCard?.count())
    }
    @Test
    fun `convert sampleData into to lists of Ints`() {
        val cardDecks = sampleData.parse()
        assertEquals(listOf(9,2,6,3,1), cardDecks[0])
        assertEquals(listOf(5,8,4,7,10), cardDecks[1])
    }
    @Test
    fun `making hands from sampleData`() {
        val (hand1, hand2) = sampleData.getHands()
        assertEquals(9, hand1.topCard?.number)
        assertEquals(5, hand2.topCard?.number)
    }
    @Test
    fun `moving cards from hand2 to hand1`() {
        val game = sampleData.getHands()
        game.moveCardsHand2ToHand1()
        assertEquals(2, game.hand1.topCard?.number)
        assertEquals(8, game.hand2.topCard?.number)
        assertEquals(5, game.hand1.bottomCard?.number)
        assertEquals(10, game.hand2.bottomCard?.number)
        println(game.hand1.topCard)
        println(game.hand2.topCard)
       assertEquals(6, game.hand1.topCard?.count())
       assertEquals(4, game.hand2.topCard?.count())
    }
    @Test
    fun `moving cards from hand1 to hand2`() {
        val game = sampleData.getHands()
        game.moveCardsHand1ToHand2()
        assertEquals(2, game.hand1.topCard?.number)
        assertEquals(8, game.hand2.topCard?.number)
        assertEquals(1, game.hand1.bottomCard?.number)
        assertEquals(9, game.hand2.bottomCard?.number)
        println(game.hand1.topCard)
        assertEquals(4, game.hand1.topCard?.count())
        assertEquals(6, game.hand2.topCard?.count())
    }
    @Test
    fun `moving top cards from both hands to the bottom`() {
        val game = sampleData.getHands()
        game.moveCardsToBottom()
        assertEquals(2, game.hand1.topCard?.number)
        assertEquals(8, game.hand2.topCard?.number)
        assertEquals(9, game.hand1.bottomCard?.number)
        assertEquals(5, game.hand2.bottomCard?.number)
        println(game.hand1.topCard)
        assertEquals(5, game.hand1.topCard?.count())
        assertEquals(5, game.hand2.topCard?.count())
    }
    @Test
    fun `play rounds using sample data`() {
        val game = sampleData.getHands()
        game.playRound()
        assertEquals(6, game.hand1.topCard?.count())
        assertEquals(4, game.hand2.topCard?.count())
        game.playRound()
        assertEquals(5, game.hand1.topCard?.count())
        assertEquals(5, game.hand2.topCard?.count())
    }
    @Test
    fun `play rounds until there is a winner`(){
        val game = sampleData.getHands()
        game.playRoundUntilWinner()
        println("hand1 value = ${game.hand1.topCard?.calcValue()}")
        println("hand2 value = ${game.hand2.topCard?.calcValue()}")
        assertEquals(306, game.hand2.topCard?.calcValue() )
    }
    @Test
    fun `part one`(){
        val game = day22Data.getHands()
        game.playRoundUntilWinner()
        println("hand1 value = ${game.hand1.topCard?.calcValue()}")
        println("hand2 value = ${game.hand2.topCard?.calcValue()}")
        assertEquals(35818, game.hand1.topCard?.calcValue() )
    }
    @Test
    fun `copying a cardNode`() {
        val cardNode = listOf(3,2,6,5,1).makeHand().topCard!!
        val (topCard, bottomCard) = cardNode.copyCardsBelow()
        assertEquals(2, topCard.number)
        assertEquals(3, topCard.count())
        assertEquals(5, bottomCard?.number)

        val cardNode1 = listOf(1,2).makeHand().topCard!!
        val (topCard1, bottomCard1) = cardNode1.copyCardsBelow()
        assertEquals(2, topCard1.number)
        assertEquals(1, topCard1.count())
        assertEquals(2, bottomCard1?.number)
    }
    @Test
    fun `hand has been played before`() {
        val game = sampleData.getHands()
        assertFalse(game.occuredAlready())
        game.previousHands["${game.hand1}"] = true
        assertFalse(game.occuredAlready())
        game.previousHands["${game.hand2}"] = true
        assertTrue(game.occuredAlready())
    }
    @Test
    fun `game that would last foreer`() {
        val sampleData = """
            Player 1:
            43
            19

            Player 2:
            2
            29
            14
        """.trimIndent().split("\n")
        val game = sampleData.getHands()
        val winner = game.playRoundUntilWinner2()
        println(winner)
    }
    @Test
    fun `finding max card below the current one`() {
        val cardNode = listOf(9,2,11,3,1).makeHand().topCard!!
        assertEquals(11, cardNode.highestCardBelowTopCard())
        val cardNode2 = listOf(9,2,6,3,8).makeHand().topCard!!
        assertEquals(8, cardNode2.highestCardBelowTopCard())
    }
    @Test
    fun `game using part two rules`() {
        val game = sampleData.getHands()
        val winner = game.playRoundUntilWinner2()
        if (winner == Game.Winner.Player1) println("Player 1 wins. Score is ${game.hand1.topCard?.calcValue()}")
        else println("Player 2 wins. Score is ${game.hand2.topCard?.calcValue()}")

    }
    @Test
    fun `part two rules`() {
        val game = day22Data.getHands()
        val winner = game.playRoundUntilWinner2()
        if (winner == Game.Winner.Player1) println("Player 1 wins. Score is ${game.hand1.topCard?.calcValue()}")
        else println("Player 2 wins. Score is ${game.hand2.topCard?.calcValue()}")
    }

    @Test
    fun `process`() {
        val (deck1, deck2) = sampleData.process()
        println(deck2.score())

        val (deck1a, deck2a) = day22Data.process()
        assertEquals(35818, deck1a.score())
    }
    @Test
    fun `process2`() {
        val loopData = """
            Player 1:
            43
            19

            Player 2:
            2
            29
            14
        """.trimIndent().split("\n")

        val (winnera, deck1a, deck2a) = sampleData.process2()
        assertEquals(0, deck1a.score())
        assertEquals(291, deck2a.score())

        val (winnerb, deck1b, deck2b) = day22Data.process2()
        assertEquals(34771, deck1b.score())
        assertEquals(0, deck2b.score())

        val (winnerc, deck1c, deck2c) = paulData.process2()
        assertEquals(0, deck1c.score())
        assertEquals(32665, deck2c.score())
    }
}