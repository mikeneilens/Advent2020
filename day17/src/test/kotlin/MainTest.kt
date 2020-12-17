import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `surrounding positions`() {
        assertEquals(26, surroundingPositions.size)
        val expectedResult = listOf(
            Position(x=-1, y=-1, z=-1),
            Position(x=-1, y=-1, z=0),
            Position(x=-1, y=-1, z=1),
            Position(x=-1, y=0, z=-1),
            Position(x=-1, y=0, z=0),
            Position(x=-1, y=0, z=1),
            Position(x=-1, y=1, z=-1),
            Position(x=-1, y=1, z=0),
            Position(x=-1, y=1, z=1),
            Position(x=0, y=-1, z=-1),
            Position(x=0, y=-1, z=0),
            Position(x=0, y=-1, z=1),
            Position(x=0, y=0, z=-1),
            Position(x=0, y=0, z=1),
            Position(x=0, y=1, z=-1),
            Position(x=0, y=1, z=0),
            Position(x=0, y=1, z=1),
            Position(x=1, y=-1, z=-1),
            Position(x=1, y=-1, z=0),
            Position(x=1, y=-1, z=1),
            Position(x=1, y=0, z=-1),
            Position(x=1, y=0, z=0),
            Position(x=1, y=0, z=1),
            Position(x=1, y=1, z=-1),
            Position(x=1, y=1, z=0),
            Position(x=1, y=1, z=1)
        )
        assertEquals(expectedResult, surroundingPositions)
    }
    @Test
    fun `no of surrounding neighbours that are actice`() {
        val data = """
            .#.
            ..#
            ###
        """.trimIndent().split("\n")

        val conwayCubes:ConwayCubes = mutableMapOf()
        updateCube(conwayCubes,data)
        assertEquals(9, conwayCubes.keys.size)
        assertEquals(State.inactive, conwayCubes[Position(0,0,0)])
        assertEquals(State.active, conwayCubes[Position(1,0,0)])
        assertEquals(State.active, conwayCubes[Position(0,2,0)])
        assertEquals(State.active, conwayCubes[Position(2,2,0)])

        assertEquals(1, conwayCubes.activeNeighbours(Position(1,0,0)))
        assertEquals(3, conwayCubes.activeNeighbours(Position(2,1,0)))
        assertEquals(3, conwayCubes.activeNeighbours(Position(1,2,0)))
        conwayCubes.print()
    }
    @Test
    fun `cycling through the data once gives the right result`() {
        val data = """
            .#.
            ..#
            ###
        """.trimIndent().split("\n")
        val conwayCubes:ConwayCubes = mutableMapOf()
        updateCube(conwayCubes,data)
        val newConwayCubes = conwayCubes.cycle()
        assertEquals(3, newConwayCubes.toList().filter{it.first.z == -1}.filter{it.second == State.active }.count())
        assertEquals(5, newConwayCubes.toList().filter{it.first.z == 0}.filter{it.second == State.active }.count())
        assertEquals(3, newConwayCubes.toList().filter{it.first.z == 1}.filter{it.second == State.active }.count())
    }
    @Test
    fun `cycling through the data three times gives the right result`() {
        val data = """
            .#.
            ..#
            ###
        """.trimIndent().split("\n")
        val conwayCubes:ConwayCubes = mutableMapOf()
        updateCube(conwayCubes,data)
        val newConwayCubes = conwayCubes.repeatCycles(3)
        assertEquals(5, newConwayCubes.toList().filter{it.first.z == -2}.filter{it.second == State.active }.count())
        assertEquals(10, newConwayCubes.toList().filter{it.first.z == -1}.filter{it.second == State.active }.count())
        assertEquals(8, newConwayCubes.toList().filter{it.first.z == 0}.filter{it.second == State.active }.count())
        assertEquals(10, newConwayCubes.toList().filter{it.first.z == 1}.filter{it.second == State.active }.count())
        assertEquals(5, newConwayCubes.toList().filter{it.first.z == 2}.filter{it.second == State.active }.count())
    }
    @Test
    fun `cycling through the data six times gives the right result`() {
        val data = """
            .#.
            ..#
            ###
        """.trimIndent().split("\n")
        val conwayCubes:ConwayCubes = mutableMapOf()
        updateCube(conwayCubes,data)
        val newConwayCubes = conwayCubes.repeatCycles(6)
        assertEquals(112, newConwayCubes.toList().filter{it.second == State.active }.count())
    }
    @Test
    fun `part one`() {
        val conwayCubes:ConwayCubes = mutableMapOf()
        updateCube(conwayCubes,day17Data)
        val newConwayCubes = conwayCubes.repeatCycles(6)
        assertEquals(263, newConwayCubes.toList().filter{it.second == State.active }.count())
    }
    @Test
    fun `cycling through the data six times gives the right result with 4 dimension conway cube`() {
        val data = """
            .#.
            ..#
            ###
        """.trimIndent().split("\n")
        val conwayCubesW:ConwayCubesW = mutableMapOf()
        updateCubeW(conwayCubesW,data)
        val newConwayCubes = conwayCubesW.repeatCycles2(6)
        assertEquals(848, newConwayCubes.toList().filter{it.second == State.active }.count())
    }
    @Test
    fun `part two`() {
        val conwayCubesW:ConwayCubesW = mutableMapOf()
        updateCubeW(conwayCubesW,day17Data)
        val newConwayCubes = conwayCubesW.repeatCycles2(6)
        assertEquals(1680, newConwayCubes.toList().filter{it.second == State.active }.count())
    }
}