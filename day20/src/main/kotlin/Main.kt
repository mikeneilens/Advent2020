import kotlin.math.sqrt

data class Position(val x:Int, val y:Int) {
    operator fun plus(other:Position) = Position(x + other.x, y + other.y)
}

//converts monster data into a list of positions
fun List<String>.toImage():List<Position> =
    flatMapIndexed { y, line ->
        line.mapIndexedNotNull{  x, char ->
            if (char == '#') Position(x, y) else null
        }
    }

data class Tile(val ref:String, var lines:List<String>) {
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

    private fun flip():Tile {
        lines = lines.flip()
        return this
    }
    fun rotate():Tile {
        lines =  lines.rotate()
        return this
    }

    fun toRight(matches:List<Match>) =
        matches.first{ it.tile == this && (it.otherTile.edges().contains(edges()[1]) || it.otherTile.edges().contains(edges()[1].reversed()))}.otherTile

    fun below(matches:List<Match>) =
        matches.first{ it.tile == this && (it.otherTile.edges().contains(edges()[2]) || it.otherTile.edges().contains(edges()[2].reversed())) }.otherTile

    fun setEdge(edge:Int, edgeToMatch:String) {
        (1..4).forEach { _ ->
            if (this.edges()[edge] == edgeToMatch.reversed()) return
            rotate()
        }
        flip()
        (1..4).forEach { _ ->
            if (this.edges()[edge] == edgeToMatch.reversed()) return
            rotate()
        }
    }
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
            tileData = mutableListOf()
        }
    }
    return tiles
}

fun List<String>.rotate():List<String> {
    val column = {col:Int -> map{it[col]}.joinToString("") }
    return mapIndexed { index,_ -> column(index).reversed()  }
}

fun List<String>.flip() = this.reversed()

data class Match(val tile:Tile, val edge:Int, val otherTile:Tile, val otherEdge:Int)

fun allMatchesBetweenAllTiles(tiles:List<Tile>):List<Match> =
    tiles.flatMap { tile ->
        tiles.flatMap { otherTile -> tile.edgesMatching(otherTile)
        }
    }.filter{it.tile != it.otherTile}

fun List<Match>.cornerTiles() = groupBy {it.tile}.filter{it.value.size == 2}.flatMap{it.value}

fun List<Match>.topLeftCorner() = groupBy {it.tile}.filter{ it.value.first().edge == 1 && it.value.last().edge == 2 }.map{it.key}.first()

fun assemble(tiles:List<Tile>, width:Int):List<Tile> {
    val allMatches = allMatchesBetweenAllTiles(tiles)
    val cornerTiles = allMatches.cornerTiles()

    val assembledTiles = mutableListOf<Tile>()
    (1..width).forEach{ rowNo ->
        if (rowNo == 1) {
            val firstTileOnLine = cornerTiles.topLeftCorner()
            assembledTiles.add(firstTileOnLine)
        } else {
            val startOfPreviousLine = assembledTiles[assembledTiles.size - width]
            val firstTileOnLine = startOfPreviousLine.below(allMatches)
            firstTileOnLine.setEdge(0, startOfPreviousLine.edges()[2])
            assembledTiles.add(firstTileOnLine)
        }
        assembledTiles.completeLine(allMatches, width - 1)
    }
    return assembledTiles
}

fun MutableList<Tile>.completeLine(matches:List<Match>, qty:Int ) {
    (1..qty).forEach { _ ->
        val nextTile = last().toRight(matches)
        nextTile.setEdge(3, last().edges()[1])
        add(nextTile)
    }
}

fun List<Tile>.createPicture(width:Int):List<String>{
    val borderlessTiles = map(::removeBorder)
    val tileSize = borderlessTiles.first().lines.size
    val rows = borderlessTiles.chunked(width)
    val lines = mutableListOf<String>()
    rows.forEach{ row ->
        (0 until tileSize).forEach { index ->
            lines.add(row.joinToString("") { tile -> tile.lines[index] })
        }
    }
    return lines
}

//fun List<String>.print() = forEach { println(it) }
fun removeBorder(tile:Tile) = Tile(tile.ref, removeBorderFrom(tile.lines))

fun removeBorderFrom(lines:List<String>) =
    lines.drop(1).dropLast(1).map{ it.drop(1).dropLast(1)}

fun List<String>.findImagesInAnyOrientation(image:List<Position>, attempts:Int = 0):Pair<List<String>, List<Position>>{
    val positions = findImage(image)
    return when {
        positions.isNotEmpty() -> Pair(this, positions)
        attempts < 4 -> rotate().findImagesInAnyOrientation(image, attempts + 1)
        else -> flip().findImagesInAnyOrientation(image, attempts + 1)
    }
}

fun List<String>.findImage(image:List<Position>):List<Position> {
    val imageMaxX = image.map { it.x }.maxOrNull() ?: 0
    val imageMaxy = image.map { it.y }.maxOrNull() ?: 0
    val maxX = first().lastIndex - imageMaxX
    val maxY = lastIndex - imageMaxy
    val result = mutableListOf<Position>()
    (0..maxY).forEach{ y ->
        (0..maxX).forEach{ x->
            if (imageFound(image,x,y)) result.add(Position(x,y))
        }
    }
    return result
}
fun List<String>.imageFound(image:List<Position>,x:Int, y:Int) =
    image.all{ position -> this[y + position.y][ x + position.x] == '#'
    }

fun Pair<List<String>,List<Position>>.countHashes(image:List<Position>):Int {
    val (picture, imagePositions) = this
    val monsterPositions = monsterPositions(imagePositions, image)
    var count = 0
    (0..picture.lastIndex).forEach { y ->
        (0..picture.first().lastIndex).forEach{ x ->
            if (picture[y][x] == '#' && !monsterPositions.contains(Position(x,y))) count ++
        }
    }
    return count
}

fun monsterPositions(monsterStartPositions:List<Position>, image:List<Position>)  =
    monsterStartPositions.flatMap{ monsterStartPosition ->
        image.map { imagePosition ->
            monsterStartPosition + imagePosition
        }
    }

fun calcSolution(data:List<String>, monsterData:List<String>):Int {
    val tiles = data.parse()
    val width = sqrt(tiles.size.toDouble()).toInt()
    val picture = assemble(tiles, width).createPicture(width)
    val pictureAndPositions = picture.findImagesInAnyOrientation(monsterData.toImage())
    return pictureAndPositions.countHashes(monsterData.toImage())
}
