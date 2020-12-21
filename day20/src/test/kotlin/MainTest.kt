import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `parse data into tiles`() {
        val tiles = sampleData.parse()
        assertEquals(9, tiles.size)
        assertEquals("..##.#..#.", tiles[0].lines[0])
        assertEquals("..###..###", tiles[0].lines[9])
    }
    @Test
    fun `tile rotated right`() {
        val tile = listOf("abcd","efgh","ijkl","mnop")
        val expected = listOf(
            "miea",
            "njfb",
            "okgc",
            "plhd")
        assertEquals(expected, tile.rotate())
    }
    @Test
    fun `tile flipped`() {
        val tile = listOf("abcd","efgh","ijkl","mnop")
        val expectedV = listOf("mnop","ijkl","efgh","abcd")
        assertEquals(expectedV, tile.flipV())
        val expectedH = listOf("dcba","hgfe","lkji","ponm")
        assertEquals(expectedH, tile.flipH())
    }
    @Test
    fun `top bottom left and right lines`() {
        val tile = listOf("abcd","efgh","ijkl","mnop")
        assertEquals("abcd", tile.topLine())
        assertEquals("mnop", tile.bottomLine())
        assertEquals("aeim", tile.leftLine())
        assertEquals("dhlp", tile.rightLine())
    }
    @Test
    fun `row is valid`() {
        val validRow = listOf(
            listOf("ab","cd"),listOf("be","df"),listOf("eg","fh")
        )
        assertTrue(validRow.rowIsValid())
    }
    @Test
    fun `col is valid`() {
        val validCol = listOf(
            listOf("ab","cd"),listOf("cd","ef"),listOf("ef","gh")
        )
        assertTrue(validCol.columIsValid())
    }
    @Test
    fun `grid is valid`() {
        val tile1 = Tile("1", listOf("ab","cd"))
        val tile2 = Tile("2", listOf("bd","df"))
        val tile3 = Tile("3", listOf("cd","gh"))
        val tile4 = Tile("3", listOf("df","hj"))
        assertTrue(listOf(tile1,tile2,tile3,tile4).validGrid())
    }
    @Test
    fun `all border values for a tile`() {
        val tile = Tile("1", listOf("abcd","efgh","ijkl","mnop"))
        assertEquals(listOf("abcd", "mnop", "aeim", "dhlp", "miea", "plhd", "ponm", "dcba"), tile.allBoarders())
    }
    @Test
    fun `border tiles in the sample data`() {
        val tiles = sampleData.parse()
        tiles.borderTiles()
    }
}