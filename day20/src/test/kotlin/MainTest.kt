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
    fun `edges`() {
        val tileData = listOf(
            "abcd", "lxxe", "kxxf", "jihg",
        )
        val expectedResult = listOf(
            "abcd","defg","ghij","jkla"
        )
        val expectedResultFlipped = listOf(
            "dcba","gfed","jihg","alkj"
        )
        assertEquals(expectedResult, Tile("1234", tileData).edges())
        assertEquals(expectedResultFlipped, Tile("1234", tileData).flippedEdges())
    }
    @Test
    fun `edges that match between two tiles`() {
        val tileData1 = listOf(
            "abcd", "lxxe", "kxxf", "jihg",
        )
        val tileData2 = listOf(
            "abcd", "lxxe", "kxxf", "jihg",
        )
        val fourMatches = Tile("tile1",lines = tileData1).edgesMatching(Tile("tile1",tileData1))
        assertEquals(4, fourMatches.size)
    }
    @Test
    fun `all matches between all tiles`() {
        val tiles = sampleData.parse()
        val result = allMatchesBetweenAllTiles(tiles)
        println(result)
    }
    @Test
    fun `find corner tiles`() {
        val tiles = sampleData.parse()
        val cornerTiles: List<Match> = cornerTiles(allMatchesBetweenAllTiles(tiles))
        assertTrue(cornerTiles.map{it.tile.ref}.contains("1951"))
        assertTrue(cornerTiles.map{it.tile.ref}.contains("3079"))
        assertTrue(cornerTiles.map{it.tile.ref}.contains("2971"))
        assertTrue(cornerTiles.map{it.tile.ref}.contains("1171"))
        assertEquals(4, cornerTiles.map{it.tile.ref}.distinct().size)
        val result = cornerTiles.map{it.tile.ref.toInt()}.distinct().fold(1L){total, value -> total * value.toLong()}
        assertEquals(20899048083289L, result)
    }
    @Test
    fun `part one`() {
        val tiles = day20Data.parse()
        val cornerTiles: List<Match> = cornerTiles(allMatchesBetweenAllTiles(tiles))
        assertEquals(4, cornerTiles.map{it.tile.ref}.distinct().size)
        val result = cornerTiles.map{it.tile.ref.toInt()}.distinct().fold(1L){total, value -> total * value.toLong()}
        assertEquals(16937516456219L, result)
    }

}