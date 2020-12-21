import kotlin.math.sqrt
import kotlin.random.Random

data class Tile(val ref:String, val lines:List<String>, var OrienttionNo:Int = 0) {
    val rotate1 = lines.rotate()
    val rotate2 = lines.rotate().rotate()
    val rotate3  = lines.rotate().rotate()
    val fliph = lines.flipH()
    val fliphr1 = lines.flipH().rotate()
    val fliphr2 = lines.flipH().rotate().rotate()
    val fliphr3 = lines.flipH().rotate().rotate().rotate()
    val flipv = lines.flipV()
    val flipvr1 = lines.flipV().rotate()
    val flipvr2 = lines.flipV().rotate().rotate()
    val flipvr3  = lines.flipV().rotate().rotate().rotate()
    val orientations = listOf(lines, rotate1, rotate2, rotate3, fliph, fliphr1, fliphr2, fliphr3, flipv, flipvr1, flipvr2, flipvr3)
}
fun List<String>.rotate():List<String> = mapIndexed{ index, _ -> map{it.get(index)}.joinToString("").reversed() }
fun List<String>.flipV():List<String> = reversed()
fun List<String>.flipH():List<String> = map{it.reversed()}

fun List<String>.topLine():String = first()
fun List<String>.bottomLine():String = last()
fun List<String>.leftLine():String = map{it.first()}.joinToString("")
fun List<String>.rightLine():String = map{it.last()}.joinToString("")
fun List<String>.borders() = listOf(topLine(), bottomLine(), leftLine(), rightLine())
fun Tile.allBoarders() = orientations.flatMap { it.borders() }.toSet()

fun List<List<String>>.rowIsValid() = windowed(2,1).all {it[0].rightLine() == it[1].leftLine()}
fun List<List<String>>.columIsValid() = windowed(2,1).all {it[0].bottomLine() == it[1].topLine()}

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

fun List<Tile>.validGrid():Boolean {
    val gridWidth = sqrt(size.toDouble()).toInt()
    val tiles = map{it.lines}
    val allRowsValid =  tiles.chunked(gridWidth).all{ it.rowIsValid()}
    val allColsValid = (0..(gridWidth - 1)).map{ col -> tiles.filterIndexed{ index, tile -> index % gridWidth == col }}
        .all{it.columIsValid()}
    return allRowsValid && allColsValid
}

fun List<Tile>.borderTiles(){
    forEach { tile ->
        val otherTiles = filter{it.ref != tile.ref}
        val otherTilesBorders = filter{it.ref != tile.ref}.flatMap { it.allBoarders() }.toSet()
        val tileAllBorders = tile.allBoarders()
        val borders = tile.allBoarders().intersect(otherTilesBorders)

        if (tile.allBoarders().intersect(otherTilesBorders).isEmpty()) {
            println("${tile.ref} is a border tile")
        }
    }
 }


fun List<Tile>.createRandomGridAndValidate():Pair<List<Tile>, Boolean> {
    val shuffledTiles = shuffled()
    return Pair(shuffledTiles, shuffledTiles.validGrid())
}