import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun `no of surrounding neighbours that are active`() {
        val data = """
            .#.
            ..#
            ###
        """.trimIndent().split("\n")


        val conwayCubes = createConwayCubes(data, dimensions = 3)
        assertEquals(9, conwayCubes.keys.size)
        assertEquals(State.Inactive, conwayCubes[listOf(0,0,0)])
        assertEquals(State.Active, conwayCubes[listOf(1,0,0)])
        assertEquals(State.Active, conwayCubes[listOf(0,2,0)])
        assertEquals(State.Active, conwayCubes[listOf(2,2,0)])

        assertEquals(1, conwayCubes.activeNeighbours(listOf(1,0,0),surroundingPositions(3)))
        assertEquals(3, conwayCubes.activeNeighbours(listOf(2,1,0),surroundingPositions(3)))
        assertEquals(3, conwayCubes.activeNeighbours(listOf(1,2,0),surroundingPositions(3)))

    }

    val Position.z:Int get() = get(2)

    @Test
    fun `cycling through the data once gives the right result`() {
        val data = """
            .#.
            ..#
            ###
        """.trimIndent().split("\n")
        val conwayCubes  = createConwayCubes(data, dimensions = 3)
        val newConwayCubes = conwayCubes.surroundActiveCubesWithInactive(3).cycle(3)
        assertEquals(3, newConwayCubes.toList().filter{it.first.z == -1}.filter{it.second == State.Active }.count())
        assertEquals(5, newConwayCubes.toList().filter{it.first.z == 0}.filter{it.second == State.Active }.count())
        assertEquals(3, newConwayCubes.toList().filter{it.first.z == 1}.filter{it.second == State.Active }.count())
    }
    @Test
    fun `cycling through the data three times gives the right result`() {
        val data = """
            .#.
            ..#
            ###
        """.trimIndent().split("\n")
        val newConwayCubes = data.process(3, 3)
        assertEquals(5, newConwayCubes.toList().filter{it.first.z == -2}.filter{it.second == State.Active }.count())
        assertEquals(10, newConwayCubes.toList().filter{it.first.z == -1}.filter{it.second == State.Active }.count())
        assertEquals(8, newConwayCubes.toList().filter{it.first.z == 0}.filter{it.second == State.Active }.count())
        assertEquals(10, newConwayCubes.toList().filter{it.first.z == 1}.filter{it.second == State.Active }.count())
        assertEquals(5, newConwayCubes.toList().filter{it.first.z == 2}.filter{it.second == State.Active }.count())
    }
    @Test
    fun `cycling through the data six times gives the right result`() {
        val data = """
            .#.
            ..#
            ###
        """.trimIndent().split("\n")
        val newConwayCubes = data.process(6, 3)
        assertEquals(112, newConwayCubes.toList().filter{it.second == State.Active }.count())
    }
    @Test
    fun `part one`() {
        val newConwayCubes = day17Data.process(6, 3)
        assertEquals(263, newConwayCubes.toList().filter{it.second == State.Active }.count())
    }
    @Test
    fun `cycling through the data six times gives the right result with 4 dimension conway cube`() {
        val data = """
            .#.
            ..#
            ###
        """.trimIndent().split("\n")
        val newConwayCubes = data.process(6, 4)
        assertEquals(848, newConwayCubes.toList().filter{it.second == State.Active }.count())
    }
    @Test
    fun `part two`() {
        val newConwayCubes = day17Data.process(6, 4)
        assertEquals(1680, newConwayCubes.toList().filter{it.second == State.Active }.count())
    }
    @Test
    fun `part two, 5 dimensions`() {
        val newConwayCubes = day17Data.process(6, 5)
        assertEquals(12440, newConwayCubes.toList().filter{it.second == State.Active }.count())
    }
    @Test
    fun `part two, 2 dimensions`() {
        val newConwayCubes = day17Data.process(6, 2)
        assertEquals(21, newConwayCubes.toList().filter{it.second == State.Active }.count())
    }
}