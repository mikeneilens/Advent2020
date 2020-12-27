import kotlin.math.sqrt
import kotlin.random.Random

data class Tile(val ref:String, val lines:List<String>, var OrienttionNo:Int = 0) {
    fun edges() = listOf(
        lines[0],
        lines.map { it.last() }.joinToString(""),
        lines.last().reversed(),
        lines.reversed().map { it.first() }.joinToString(""),
    )
    fun flippedEdges() = edges().map{it.reversed()}

    fun edgesMatching(otherTile:Tile): List<Match> =

        edges().flatMapIndexed{thisIndex, edge ->
            otherTile.edges().mapIndexedNotNull { otherIndex, otherEdge ->
                if (otherEdge == edge) {
                    Match(this, thisIndex, otherTile, otherIndex)
                } else {
                    null
                }
            }
        } +
        edges().flatMapIndexed{thisIndex, edge ->
            otherTile.flippedEdges().mapIndexedNotNull { otherIndex, otherEdge ->
                if (otherEdge == edge) {
                    Match(this, thisIndex, otherTile, otherIndex + 4)
                } else {
                    null
                }
            }
        }

}

data class Match(val tile:Tile, val edge:Int, val otherTile:Tile, val otherEdge:Int)

fun allMatchesBetweenAllTiles(tiles:List<Tile>):List<Match> =
    tiles.flatMap { tile ->
        tiles.flatMap { otherTile -> tile.edgesMatching(otherTile)
        }
    }.filter{it.tile != it.otherTile}

fun cornerTiles(allMatches:List<Match>) = allMatches.groupBy {it.tile}.filter{it.value.size == 2 }.flatMap{it.value}

fun topLeftCorner(cornerTile:List<Match>) {

}


fun List<String>.parse():List<Tile> {
    val tiles = mutableListOf<Tile>()
    var ref = ""
    var tileData = mutableListOf<String>()
    forEach { line ->
        if (line.startsWith("Tile")) {
            ref = line.substring(5,9)
        } else {
            if (line.isNotEmpty()){
                tileData.add(line)
            }
        }
        if (tileData.size == 10) {
            tiles.add(Tile(ref, tileData))
            tileData = mutableListOf<String>()
            tileData = mutableListOf<String>()
        }
    }
    return tiles
}

