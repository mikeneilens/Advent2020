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
        val fourMatches = Tile("tile1",lines = tileData1).edgesMatching(Tile("tile1",tileData1))
        assertEquals(4, fourMatches.size)
    }
    @Test
    fun `find corner tiles`() {
        val tiles = sampleData.parse()
        val cornerTiles: List<Match> = allMatchesBetweenAllTiles(tiles).cornerTiles()
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
        val cornerTiles: List<Match> = allMatchesBetweenAllTiles(tiles).cornerTiles()
        assertEquals(4, cornerTiles.map{it.tile.ref}.distinct().size)
        val result = cornerTiles.map{it.tile.ref.toInt()}.distinct().fold(1L){total, value -> total * value.toLong()}
        assertEquals(16937516456219L, result)
    }
    @Test
    fun `find top left corner tile`() {
        val tiles = sampleData.parse()
        val cornerTiles: List<Match> = allMatchesBetweenAllTiles(tiles).cornerTiles()
        val topLeft = cornerTiles.topLeftCorner()
        assertEquals("2971", topLeft.ref)
    }
    @Test
    fun `find tile to the right of a tile`() {
        val tiles = sampleData.parse()
        val matches = allMatchesBetweenAllTiles(tiles)
        val cornerTiles: List<Match> = matches.cornerTiles()
        val topLeft = cornerTiles.topLeftCorner()

        val tileToRight = topLeft.toRight(matches)
        assertEquals("1489", tileToRight.ref)
    }
    @Test
    fun `find tile below a tile`() {
        val tiles = sampleData.parse()
        val matches = allMatchesBetweenAllTiles(tiles)
        val cornerTiles: List<Match> = matches.cornerTiles()
        val topLeft = cornerTiles.topLeftCorner()

        val below = topLeft.below(matches)
        assertEquals("2729", below.ref)
    }
    @Test
    fun `rotating a tile`() {
        val tileData = listOf("1234", "5678", "9abc", "defg")
        val tile = Tile("test",tileData)
        tile.rotate()
        assertEquals(listOf("d951","ea62","fb73","gc84"), tile.lines)
        tile.rotate()
        tile.rotate()
        tile.rotate()
        assertEquals(listOf("1234", "5678", "9abc", "defg"), tile.lines)
    }
    @Test
    fun `tiles is rotated so left edge matches the right edge of the tile before it`() {
        val tiles = sampleData.parse()
        val matches = allMatchesBetweenAllTiles(tiles)
        val cornerTiles: List<Match> = matches.cornerTiles()
        val topLeft = cornerTiles.topLeftCorner()
        val topLeftRightEdge = topLeft.edges()[1]
        val tileToRight = topLeft.toRight(matches)
        tileToRight.setEdge(3, topLeftRightEdge)
        assertEquals("#.#.##...#", tileToRight.edges()[3])
    }
    @Test
    fun `assemble tiles into a picture`() {
        val tiles = sampleData.parse()
        val assembledTiles = assemble(tiles,3)
        assertEquals("2971", assembledTiles[0].ref)
        assertEquals("1489", assembledTiles[1].ref)
        assertEquals("1171", assembledTiles[2].ref)
        assertEquals("2729", assembledTiles[3].ref)
        assertEquals("1427", assembledTiles[4].ref)
        assertEquals("2473", assembledTiles[5].ref)
        assertEquals("1951", assembledTiles[6].ref)
        assertEquals("2311", assembledTiles[7].ref)
        assertEquals("3079", assembledTiles[8].ref)
    }
    @Test
    fun `assemble tiles into a picture using full data`() {
        val tiles = day20Data.parse()
        val assembledTiles = assemble(tiles,12)
        assertEquals("1747", assembledTiles[0].ref)
        assertEquals("3709", assembledTiles[8].ref)
    }
    @Test
    fun `removing the border from the lines for a tile`() {
        val data = listOf(
            "aaaaa",
            "b123b",
            "c456c",
            "d789d",
            "eeeee"
        )
        val expectedResult = listOf(
            "123",
            "456",
            "789"
        )
        assertEquals(expectedResult, removeBorderFrom(data))
    }
    @Test
    fun `creating a picture from list of tiles`() {
        val tiles = sampleData.parse()
        val assembledTiles = assemble(tiles,3)
        val picture = assembledTiles.createPicture(3)
        assertEquals(24, picture[0].length)
        assertEquals(24, picture.size)
    }
    @Test
    fun `creating a picture from list of tiles using real data`() {
        val tiles = day20Data.parse()
        val assembledTiles = assemble(tiles,12)
        val picture = assembledTiles.createPicture(12)
        assertEquals(96, picture[0].length)
        assertEquals(96, picture.size)
    }
    @Test
    fun `converting monster into a list of positions`() {
        val result = monsterData.toImage()
        assertEquals(15, result.size)
       assertEquals(Position(18,0), result[0])
       assertEquals(Position(0,1), result[1])
       assertEquals(Position(5,1), result[2])
       assertEquals(Position(6,1), result[3])
       assertEquals(Position(11,1), result[4])
       assertEquals(Position(12,1), result[5])
       assertEquals(Position(17,1), result[6])
       assertEquals(Position(18,1), result[7])
       assertEquals(Position(19,1), result[8])
       assertEquals(Position(1,2), result[9])
       assertEquals(Position(4,2), result[10])
       assertEquals(Position(7,2), result[11])
       assertEquals(Position(10,2), result[12])
       assertEquals(Position(13,2), result[13])
       assertEquals(Position(16,2), result[14])
    }
    @Test
    fun `positions of monsters in a picture`() {
        val picture = listOf(
            "   #                  ",
            "    #      #       #  ",
            " #    ##  # ##    ### ",
            "  #  #  #  #  #  #    ",
            "  #  ##  #  ##  #  #  ",
            "  #  #  #  #  #  #    ",
            "                    # ",
            "  #    ##    ##    ###",
            "   #  #  #  #  #  #   "
        )
        val result = picture.findImage(monsterData.toImage())
        assertEquals (2, result.size)
        assertEquals (Position(1,1), result[0])
        assertEquals (Position(2,6), result[1])
    }
    @Test
    fun `positions of monsters in the test datat`() {
        val picture = assemble(sampleData.parse(),3).createPicture(3)
        val (_, result) = picture.findImagesInAnyOrientation(monsterData.toImage())
        assertEquals(Position(2,2), result[0])
        assertEquals(Position(1,16), result[1])
    }
    @Test
    fun `number of hashes that are not monsters in the test data`() {
        val result = calcSolution(sampleData, monsterData)
        assertEquals(273,result)
    }
    @Test
    fun `part two`() {
        val result = calcSolution(day20Data, monsterData)
        assertEquals(1858,result)
    }
}