const val white = "white"
const val black = "black"

data class Vector(val x:Int, val y:Int) {
    operator fun plus(other:Vector) = Vector(this.x + other.x, this.y + other.y)
}

enum class Step(val move:Vector) {
    East  (Vector(2,0)),
    SouthEast (Vector(1,1) ),
    SouthWest (Vector(-1,1)),
    West  (Vector(-2,0)),
    NorthWest (Vector(-1,-1)),
    NorthEast (Vector(1,-1))
}
fun String.toStep() = when(true) {
    startsWith("e") -> Pair(Step.East,removePrefix("e"))
    startsWith("se") -> Pair(Step.SouthEast,removePrefix("se"))
    startsWith("sw") -> Pair(Step.SouthWest,removePrefix("sw"))
    startsWith("w") -> Pair(Step.West,removePrefix("w"))
    startsWith("nw") -> Pair(Step.NorthWest,removePrefix("nw"))
    startsWith("ne") -> Pair(Step.NorthEast,removePrefix("ne"))
    else -> Pair(Step.East,removePrefix("e"))
}
fun String.toSteps():List<Step> {
    var string = this
    val result = mutableListOf<Step>()
    while (string.isNotEmpty()) {
        val (step, newString) = string.toStep()
        string = newString
        result.add(step)
    }
    return result
}
fun List<Step>.findTilePosition():Vector = fold(Vector(0,0)){result, step -> result + step.move}

fun MutableMap<Vector, String>.flipTileAt(vector:Vector) {
    val tileColor = get(vector) ?: white
    set(vector , if (tileColor == white) black else white)
}

fun List<String>.flipTiles():Map<Vector,String> {
    val map = mutableMapOf<Vector, String>()
    forEach { line ->
        val tilePosition = line.toSteps().findTilePosition()
        map.flipTileAt(tilePosition)
    }
    return map
}

fun Map<Vector,String>.blackTiles() = values.filter{ it == black}.size

fun Vector.adjacentPositions() = Step.values().map{ this + it.move}

fun MutableMap<Vector, String>.makeEmptyAdjacentTilesWhite() {
    keys.toList().forEach { tilePosition ->
        tilePosition.adjacentPositions().forEach { adjacentPosition ->
            if (get(adjacentPosition) == null) set(adjacentPosition, white)
        }
    }
}

fun Vector.adjacentBlackTiles(map:Map<Vector, String>) = adjacentPositions().count { map[it] == black   }

fun MutableMap<Vector, String>.flipTilesUsingPartTwoRules() {
    makeEmptyAdjacentTilesWhite()
    val tilesToChange = keys.mapNotNull (this::newTileStateOrNull)
    updateTiles(tilesToChange)
}

fun Map<Vector, String>.newTileStateOrNull(tilePosition:Vector):Pair<Vector, String>? {
    val noOfAdjacentBlackTiles = tilePosition.adjacentBlackTiles(this)
    if (get(tilePosition) == black && noOfAdjacentBlackTiles == 0 || noOfAdjacentBlackTiles > 2) return Pair(tilePosition, white)
    if (get(tilePosition) == white && noOfAdjacentBlackTiles == 2) return Pair(tilePosition, black)
    return  null
}

fun MutableMap<Vector, String>.updateTiles(tilesToChange:List<Pair<Vector, String>>) =
    tilesToChange.forEach{(vector,color) -> set(vector, color) }

fun MutableMap<Vector, String>.repeatFlips(qty:Int) {
    (1..qty).forEach { _ -> flipTilesUsingPartTwoRules() }
}
