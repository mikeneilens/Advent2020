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
        val endingPosition = "nwwswee".toSteps().findTilePosition()
        assertEquals(Vector(0,0), endingPosition)
    }
    @Test
    fun `flipping tiles from sample data`() {
        val noOfBlackTiles = sampleData.flipTiles().blackTiles()
        assertEquals(10, noOfBlackTiles)
    }
    @Test
    fun `part one`() {
        val noOfBlackTiles = day24Data.flipTiles().blackTiles()
        assertEquals(521, noOfBlackTiles)
    }
}