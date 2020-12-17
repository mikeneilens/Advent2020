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

        val conwayCubes:ConwayCubes = mutableMapOf()
        updateCube(conwayCubes,data, dimensions = 3)
        assertEquals(9, conwayCubes.keys.size)
        assertEquals(State.Inactive, conwayCubes[listOf(0,0,0)])
        assertEquals(State.Active, conwayCubes[listOf(1,0,0)])
        assertEquals(State.Active, conwayCubes[listOf(0,2,0)])
        assertEquals(State.Active, conwayCubes[listOf(2,2,0)])

        assertEquals(1, conwayCubes.activeNeighbours(listOf(1,0,0),surroundingPositions(3)))
        assertEquals(3, conwayCubes.activeNeighbours(listOf(2,1,0),surroundingPositions(3)))
        assertEquals(3, conwayCubes.activeNeighbours(listOf(1,2,0),surroundingPositions(3)))

    }
    @Test
    fun `cycling through the data once gives the right result`() {
        val data = """
            .#.
            ..#
            ###
        """.trimIndent().split("\n")
        val conwayCubes:ConwayCubes = mutableMapOf()
        updateCube(conwayCubes,data, dimensions = 3)
        val newConwayCubes = conwayCubes.cycle(3)
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
        val conwayCubes:ConwayCubes = mutableMapOf()
        updateCube(conwayCubes,data, dimensions = 3)
        val newConwayCubes = conwayCubes.repeatCycles(3,3)
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
        val conwayCubes:ConwayCubes = mutableMapOf()
        updateCube(conwayCubes,data, dimensions = 3)
        val newConwayCubes = conwayCubes.repeatCycles(6,3)
        assertEquals(112, newConwayCubes.toList().filter{it.second == State.Active }.count())
    }
    @Test
    fun `part one`() {
        val conwayCubes:ConwayCubes = mutableMapOf()
        updateCube(conwayCubes,day17Data, dimensions = 3)
        val newConwayCubes = conwayCubes.repeatCycles(6,3)
        assertEquals(263, newConwayCubes.toList().filter{it.second == State.Active }.count())
    }
    @Test
    fun `cycling through the data six times gives the right result with 4 dimension conway cube`() {
        val data = """
            .#.
            ..#
            ###
        """.trimIndent().split("\n")
        val conwayCubes:ConwayCubes = mutableMapOf()
        updateCube(conwayCubes,data,4)
        val newConwayCubes = conwayCubes.repeatCycles(6,4)
        assertEquals(848, newConwayCubes.toList().filter{it.second == State.Active }.count())
    }
    @Test
    fun `part two`() {
        val conwayCubesW:ConwayCubes = mutableMapOf()
        updateCube(conwayCubesW,day17Data,4)
        val newConwayCubes = conwayCubesW.repeatCycles(6, 4)
        assertEquals(1680, newConwayCubes.toList().filter{it.second == State.Active }.count())
    }
    @Test
    fun `part two, 5 dimensions`() {
        val conwayCubesW:ConwayCubes = mutableMapOf()
        updateCube(conwayCubesW,day17Data,5)
        val newConwayCubes = conwayCubesW.repeatCycles(6, 5)
        assertEquals(12440, newConwayCubes.toList().filter{it.second == State.Active }.count())
    }
    @Test
    fun `part two, 2 dimensions`() {
        val conwayCubesW:ConwayCubes = mutableMapOf()
        updateCube(conwayCubesW,day17Data,2)
        val newConwayCubes = conwayCubesW.repeatCycles(6, 2)
        assertEquals(21, newConwayCubes.toList().filter{it.second == State.Active }.count())
    }
}