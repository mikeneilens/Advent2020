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
    fun `find edges of a tile`() {
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
    fun `find edge tiles`() {
        val tiles = sampleData.parse()
        val edgeTiles: List<Match> = edgeTiles(allMatchesBetweenAllTiles(tiles))
        assertTrue(edgeTiles.map{it.tile.ref}.contains("2311"))
        assertTrue(edgeTiles.map{it.tile.ref}.contains("2729"))
        assertTrue(edgeTiles.map{it.tile.ref}.contains("2473"))
        assertTrue(edgeTiles.map{it.tile.ref}.contains("1489"))
        assertEquals(4, edgeTiles.map{it.tile.ref}.distinct().size)
    }
    @Test
    fun `part one`() {
        val tiles = day20Data.parse()
        val cornerTiles: List<Match> = cornerTiles(allMatchesBetweenAllTiles(tiles))
        assertEquals(4, cornerTiles.map{it.tile.ref}.distinct().size)
        val result = cornerTiles.map{it.tile.ref.toInt()}.distinct().fold(1L){total, value -> total * value.toLong()}
        assertEquals(16937516456219L, result)
    }
    @Test
    fun `find top left corner tile`() {
        val tiles = sampleData.parse()
        val cornerTiles: List<Match> = cornerTiles(allMatchesBetweenAllTiles(tiles))
        val topLeft = topLeftCorner(cornerTiles)
        assertEquals("2971", topLeft.ref)
    }
    @Test
    fun `find tile to the right of a tile`() {
        val tiles = sampleData.parse()
        val matches = allMatchesBetweenAllTiles(tiles)
        val cornerTiles: List<Match> = cornerTiles(matches)
        val topLeft = topLeftCorner(cornerTiles)

        val (_, _, tileToRight, matchingEdge) = topLeft.toRight(matches)
        assertEquals("1489", tileToRight.ref)
        assertEquals(7, matchingEdge)
    }
    @Test
    fun `find tile below a tile`() {
        val tiles = sampleData.parse()
        val matches = allMatchesBetweenAllTiles(tiles)
        val cornerTiles: List<Match> = cornerTiles(matches)
        val topLeft = topLeftCorner(cornerTiles)

        val (_, _, below, matchingEdge) = topLeft.below(matches)
        assertEquals("2729", below.ref)
        assertEquals(4, matchingEdge)
    }
    @Test
    fun `rotating a tile`() {
        val tileData = listOf("1234", "5678", "9abc", "defg",)
        val tile = Tile("test",tileData)
        tile.rotate()
        assertEquals(listOf("d951","ea62","fb73","gc84"), tile.lines)
        tile.rotate()
        tile.rotate()
        tile.rotate()
        assertEquals(listOf("1234", "5678", "9abc", "defg",), tile.lines)
    }
    @Test
    fun `tiles is rotated so left edge matches the right edge of the tile before it`() {
        val tiles = sampleData.parse()
        val matches = allMatchesBetweenAllTiles(tiles)
        val cornerTiles: List<Match> = cornerTiles(matches)
        val topLeft = topLeftCorner(cornerTiles)
        val topLeftRightEdge = topLeft.edges()[1]
        val (_, _, tileToRight, matchingEdge) = topLeft.toRight(matches)
        tileToRight.setEdge(3, topLeftRightEdge)
        assertEquals("#.#.##...#", tileToRight.edges()[3])
    }
    @Test
    fun `create picture`() {
        val tiles = sampleData.parse()
        val picture = createPicture(tiles)
        assertEquals("2971", picture[0].ref)
        assertEquals("1489", picture[1].ref)
        assertEquals("1171", picture[2].ref)
        assertEquals("2729", picture[3].ref)
        assertEquals("1427", picture[4].ref)
        assertEquals("2473", picture[5].ref)
        assertEquals("1951", picture[6].ref)
        assertEquals("2311", picture[7].ref)
        assertEquals("3079", picture[8].ref)
    }
    @Test
    fun `create picture using full data`() {
        val tiles = day20Data.parse()
        val picture = createPicture(tiles)
        assertEquals("1747", picture[0].ref)
        assertEquals("1747", picture[8].ref)
    }
}