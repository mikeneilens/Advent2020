import kotlin.math.sqrt

data class Tile(val ref:String, var lines:List<String>, var used:Boolean = false) {
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

    fun flip():Tile {
        lines = lines.reversed()
        return this
    }
    fun rotate():Tile {
        val column = {col:Int -> lines.map{it[col]}.joinToString("") }
        lines =  lines.mapIndexed { index,_ -> column(index).reversed()  }
        return this
    }
}

data class Match(val tile:Tile, val edge:Int, val otherTile:Tile, val otherEdge:Int)

fun allMatchesBetweenAllTiles(tiles:List<Tile>):List<Match> =
    tiles.flatMap { tile ->
        tiles.flatMap { otherTile -> tile.edgesMatching(otherTile)
        }
    }.filter{it.tile != it.otherTile}

fun cornerTiles(allMatches:List<Match>) = allMatches.groupBy {it.tile}.filter{it.value.size == 2 }.flatMap{it.value}
fun edgeTiles(allMatches:List<Match>) = allMatches.groupBy {it.tile}.filter{it.value.size == 3 }.flatMap{it.value}

fun topLeftCorner(cornerTiles:List<Match>) =
cornerTiles.groupBy { it.tile }.filter{ it.value.first().edge == 1 && it.value.last().edge == 2 }.map{it.key}.first()

fun Tile.toRight(matches:List<Match>) =
    matches.first{ it.tile == this && it.edge == 1 }

fun Tile.below(matches:List<Match>) =
    matches.first{ it.tile == this && it.edge == 2 }



fun Tile.setEdge(edge:Int, edgeToMatch:String) {
    (1..4).forEach {
        if (this.edges()[edge] == edgeToMatch.reversed()) return
        rotate()
    }
    flip()
    (1..4).forEach {
        if (this.edges()[edge] == edgeToMatch.reversed()) return
        rotate()
    }
}

fun createPicture(tiles:List<Tile>):List<Tile> {
    val width = sqrt(tiles.size.toDouble()).toInt()
    val picture = mutableListOf<Tile>()
    val allMatches = allMatchesBetweenAllTiles(tiles)
    val cornerTiles = cornerTiles((allMatches))
    val edgeTiles = edgeTiles((allMatches))
    val matchesContainingEdges = allMatches.filter{edgeTiles.map{it.tile}.contains(it.otherTile)}
    val matchesContainingCorners = allMatches.filter{cornerTiles.map{it.tile}.contains(it.otherTile)}


    (1..width).forEach{
        if (it == 1) {
            val firstTileOnLine = topLeftCorner(cornerTiles)
            firstTileOnLine.used = true
            picture.add(firstTileOnLine)

            picture.completeLine(matchesContainingEdges, width - 2)

            val (_, _, lastTileOnRow, _) = picture.last().toRight(matchesContainingCorners)
            lastTileOnRow.setEdge(3, picture.last().edges()[1])
            lastTileOnRow.used
            picture.add(lastTileOnRow)
        } else {
            val (_, _, firstTileOnLine, _) = picture[picture.size - width].below(allMatches)
            firstTileOnLine.setEdge(0, picture[picture.size - width].edges()[2])
            firstTileOnLine.used = true
            picture.add(firstTileOnLine)
            picture.completeLine(allMatches, width - 2)
            val (_, _, lastTileOnRow, _) = picture.last().toRight(allMatches)
            lastTileOnRow.setEdge(3, picture.last().edges()[1])
            lastTileOnRow.used
            picture.add(lastTileOnRow)
        }
    }

    return picture
}

fun MutableList<Tile>.completeLine(matches:List<Match>, qty:Int ) {
    if (qty == 0) return
    val lastTile = last()
    val (_, _, nextTile, _) = lastTile.toRight(matches)
    nextTile.setEdge(3, lastTile.edges()[1])
    nextTile.used
    add(nextTile)
    completeLine(matches, qty - 1)
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

