import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs

class MainTest {
    val sampleData1 = """
        F10
        N3
        F7
        R90
        F11
    """.trimIndent().split("\n")
    @Test
    fun `adding two positions`() {
        val p1 = Position(1,2)
        val p2 = Position(6,8)
        assertEquals(Position(7,10), p1 + p2)
    }
    @Test
    fun `multiplying a position by 7`() {
        val p1 = Position(6,8)
        assertEquals(Position(42,56), p1 * 7)
    }
    @Test
    fun `rotating 270 degrees`() {
        assertEquals(Orientation.East, Orientation.North.rotateRight(90))
        assertEquals(Orientation.South, Orientation.North.rotateRight(180))
        assertEquals(Orientation.West , Orientation.North.rotateRight(270) )
        assertEquals(Orientation.West , Orientation.North.rotateLeft(90) )
        assertEquals(Orientation.South , Orientation.North.rotateLeft(180) )
        assertEquals(Orientation.East , Orientation.North.rotateLeft(270) )
    }
    @Test
    fun `final position of sampleData`() {
        val (position,orientiation) = partOne(sampleData1)
        assertEquals(Position(17,8), position)
        assertEquals(Orientation.South, orientiation)
        assertEquals(25, abs(position.x) + abs(position.y))
    }
    @Test
    fun `part one`() {
        val (position,orientiation) = partOne(day12Data)
        assertEquals(Position(426,478), position)
        assertEquals(Orientation.North, orientiation)
        assertEquals(904, abs(position.x) + abs(position.y))
    }
    @Test
    fun `rotating  wayPoint 360 degrees`() {
        assertEquals(Position(4,10),   Position(10,-4).rotateRight(90))
        assertEquals(Position(-10,4),   Position(10,-4).rotateRight(180))
        assertEquals(Position(-4,-10),   Position(10,-4).rotateRight(270))
        assertEquals(Position(10,-4),   Position(10,-4).rotateRight(360))

        assertEquals(Position(-4,-10),   Position(10,-4).rotateLeft(90))
        assertEquals(Position(-10,4),   Position(10,-4).rotateLeft(180))
        assertEquals(Position(4,10),   Position(10,-4).rotateLeft(270))
        assertEquals(Position(10,-4),   Position(10,-4).rotateLeft(360))

    }
    @Test
    fun `final position part 2 of sampleData`() {
        val (position, wayPoint) = partTwo(sampleData1)
        assertEquals(Position(214,72), position)
        assertEquals(286, abs(position.x) + abs(position.y))
    }
    @Test
    fun `part two`() {
        val (position, wayPoint) = partTwo(day12Data)
        assertEquals(Position(-10766,7981), position)
        assertEquals(18747, abs(position.x) + abs(position.y))
    }
}