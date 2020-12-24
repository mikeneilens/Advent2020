import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = """
        sesenwnenenewseeswwswswwnenewsewsw
        neeenesenwnwwswnenewnwwsewnenwseswesw
        seswneswswsenwwnwse
        nwnwneseeswswnenewneswwnewseswneseene
        swweswneswnenwsewnwneneseenw
        eesenwseswswnenwswnwnwsewwnwsene
        sewnenenenesenwsewnenwwwse
        wenwwweseeeweswwwnwwe
        wsweesenenewnwwnwsenewsenwwsesesenwne
        neeswseenwwswnwswswnw
        nenwswwsewswnenenewsenwsenwnesesenew
        enewnwewneswsewnwswenweswnenwsenwsw
        sweneswneswneneenwnewenewwneswswnese
        swwesenesewenwneswnwwneseswwne
        enesenwswwswneneswsenwnewswseenwsese
        wnwnesenesenenwwnenwsewesewsesesew
        nenewswnwewswnenesenwnesewesw
        eneswnwswnwsenenwnwnwwseeswneewsenese
        neswnwewnwnwseenwseesewsenwsweewe
        wseweeenwnesenwwwswnew
    """.trimIndent().split("\n")

    @Test
    fun `test parsing a string into a list of steps`() {
        val result = "eseswwnwne".toSteps()
        val expectedResult = listOf(Step.East,Step.SouthEast,Step.SouthWest,Step.West,Step.NorthWest,Step.NorthEast)
        assertEquals(expectedResult, result)
    }
    @Test
    fun `nwwswee returns to self`() {
        val endingPosition = "nwwswee".toSteps().positionAfterLastStep()
        assertEquals(Vector(0,0), endingPosition)
    }
    @Test
    fun `flipping tiles from sample data`() {
        val noOfBlackTiles = sampleData.flipTiles().noOfBlackTiles()
        assertEquals(10, noOfBlackTiles)
    }
    @Test
    fun `part one`() {
        val noOfBlackTiles = day24Data.flipTiles().noOfBlackTiles()
        assertEquals(521, noOfBlackTiles)
    }
    //Part two
    @Test
    fun `set any adjacent tiles to tiles in a map to white`() {
        val map = mutableMapOf( Vector(1,1) to TileColor.Black, Vector(7,8) to TileColor.Black )
        map.makeEmptyAdjacentTilesWhite()

        val result = map.keys.toSet()
        val expectedResult = setOf(
            Vector(x=1, y=1), Vector(x=7, y=8), Vector(x=3, y=1), Vector(x=2, y=2), Vector(x=0, y=2), Vector(x=-1, y=1), Vector(x=0, y=0), Vector(x=2, y=0), Vector(x=9, y=8), Vector(x=8, y=9), Vector(x=6, y=9), Vector(x=5, y=8), Vector(x=6, y=7), Vector(x=8, y=7)
        )
        assertEquals(14, result.size)
        assertEquals(expectedResult, result)
    }
    @Test
    fun `flipping tiles using part two rules`() {
        val tiles = sampleData.flipTiles().toMutableMap()
        tiles.flipTilesUsingPartTwoRules()
        assertEquals(15, tiles.noOfBlackTiles())
        tiles.flipTilesUsingPartTwoRules()
        assertEquals(12, tiles.noOfBlackTiles())
    }
    @Test
    fun `flipping tiles 100 times using part two rules`() {
        val tiles = sampleData.flipTiles().toMutableMap()
        tiles.repeatFlips(100)
        assertEquals(2208, tiles.noOfBlackTiles())
    }
    @Test
    fun `part two`() {
        val tiles = day24Data.flipTiles().toMutableMap()
        tiles.repeatFlips(100)
        assertEquals(4242, tiles.noOfBlackTiles())
    }
}