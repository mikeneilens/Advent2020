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
fun List<Step>.findTilePosition():Vector {
    var position = Vector(0,0)
    forEach { step ->
        position += step.move
    }
    return position
}

fun List<String>.flipTiles():Map<Vector,String> {
    val map = mutableMapOf<Vector, String>()
    forEach { line ->
        val tilePosition = line.toSteps().findTilePosition()
        val tileColor = map[tilePosition] ?: white
        map[tilePosition] = if (tileColor == white) black else white
    }
    return map
}

fun Map<Vector,String>.blackTiles() = values.filter{ it == black}.size