enum class TileColor {Black, White}

data class Vector(val x:Int, val y:Int) {
    operator fun plus(other:Vector) = Vector(this.x + other.x, this.y + other.y)
}

typealias Floor = Map<Vector, TileColor>
typealias MutableFloor = MutableMap<Vector, TileColor>

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

fun MutableFloor.flipTileAt(vector:Vector) {
    val tileColor = get(vector) ?: TileColor.White
    set(vector , if (tileColor == TileColor.White) TileColor.Black else TileColor.White)
}

fun List<String>.flipTiles():Map<Vector,TileColor> {
    val mutableFloor = mutableMapOf<Vector, TileColor>()
    forEach { line ->
        val tilePosition = line.toSteps().findTilePosition()
        mutableFloor.flipTileAt(tilePosition)
    }
    return mutableFloor
}

fun Map<Vector,TileColor>.blackTiles() = values.filter{ it == TileColor.Black}.size

//part two

fun Vector.adjacentPositions() = Step.values().map{ this + it.move}

fun MutableFloor.makeEmptyAdjacentTilesWhite() {
    keys.toList().forEach { tilePosition ->
        tilePosition.adjacentPositions().forEach { adjacentPosition ->
            if (get(adjacentPosition) == null) set(adjacentPosition, TileColor.White)
        }
    }
}

fun Vector.noOfadjacentBlackTiles(floor:Floor) = adjacentPositions().count { floor[it] == TileColor.Black   }

fun MutableFloor.flipTilesUsingPartTwoRules() {
    makeEmptyAdjacentTilesWhite()
    val tilesToChange = keys.mapNotNull (this::newTileStateForAPositionOrNull)
    updateTiles(tilesToChange)
}

fun Floor.newTileStateForAPositionOrNull(tilePosition:Vector):Pair<Vector, TileColor>? {
    val noOfAdjacentBlackTiles = tilePosition.noOfadjacentBlackTiles(this)
    if (get(tilePosition) == TileColor.Black && noOfAdjacentBlackTiles == 0 || noOfAdjacentBlackTiles > 2) return Pair(tilePosition, TileColor.White)
    if (get(tilePosition) == TileColor.White && noOfAdjacentBlackTiles == 2) return Pair(tilePosition, TileColor.Black)
    return  null
}

fun MutableFloor.updateTiles(tilesToChange:List<Pair<Vector, TileColor>>) =
    tilesToChange.forEach{(vector,color) -> set(vector, color) }

fun MutableFloor.repeatFlips(qty:Int) {
    (1..qty).forEach { _ -> flipTilesUsingPartTwoRules() }
}
