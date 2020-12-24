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

fun String.toSteps():List<Step>{
    var i = 0
    val result = mutableListOf<Step>()
    while (i < length) {
        result.add(toStep(i))
        i += if ("sn".contains(get(i))) 2 else 1
    }
    return result
}

fun String.toStep(i:Int) = when(get(i)) {
        'e' -> Step.East
        'w' -> Step.West
        's' -> if (get(i+1) == 'e') Step.SouthEast else Step.SouthWest
        'n' -> if (get(i+1) == 'e') Step.NorthEast else Step.NorthWest
        else -> Step.East
}

fun List<Step>.positionAfterLastStep():Vector = fold(Vector(0,0)){ result, step -> result + step.move}

fun MutableFloor.flipTileAt(vector:Vector):MutableFloor {
    val tileColor = get(vector) ?: TileColor.White
    set(vector , if (tileColor == TileColor.White) TileColor.Black else TileColor.White)
    return this
}

fun List<String>.flipTiles():MutableFloor =
     fold(mutableMapOf()){ mutableFloor, line ->
        mutableFloor.flipTileAt(line.toSteps().positionAfterLastStep())
    }

fun Map<Vector,TileColor>.noOfBlackTiles() = values.filter{ it == TileColor.Black}.size

//part two

fun Vector.adjacentPositions() = Step.values().map{ this + it.move}

fun MutableFloor.makeEmptyAdjacentTilesWhite() {
    keys.toList().forEach { tilePosition ->
        tilePosition.adjacentPositions().forEach { adjacentPosition ->
            if (get(adjacentPosition) == null) set(adjacentPosition, TileColor.White)
        }
    }
}

fun Vector.noOfAdjacentBlackTiles(floor:Floor) = adjacentPositions().count { floor[it] == TileColor.Black   }

fun MutableFloor.flipTilesUsingPartTwoRules() {
    makeEmptyAdjacentTilesWhite()
    val tilesToChange = keys.mapNotNull (this::newTileStateForAPositionOrNull)
    updateTiles(tilesToChange)
}

fun Floor.newTileStateForAPositionOrNull(tilePosition:Vector):Pair<Vector, TileColor>? {
    val noOfAdjacentBlackTiles = tilePosition.noOfAdjacentBlackTiles(this)
    if (get(tilePosition) == TileColor.Black && noOfAdjacentBlackTiles == 0 || noOfAdjacentBlackTiles > 2) return Pair(tilePosition, TileColor.White)
    if (get(tilePosition) == TileColor.White && noOfAdjacentBlackTiles == 2) return Pair(tilePosition, TileColor.Black)
    return  null
}

fun MutableFloor.updateTiles(tilesToChange:List<Pair<Vector, TileColor>>) =
    tilesToChange.forEach{(vector,color) -> set(vector, color) }

fun MutableFloor.repeatFlips(qty:Int) {
    (1..qty).forEach { _ -> flipTilesUsingPartTwoRules() }
}
