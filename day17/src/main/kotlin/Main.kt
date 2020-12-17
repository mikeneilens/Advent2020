import kotlin.math.pow
typealias PositionL = List<Int>

operator fun PositionL.plus(other:PositionL) = this.zip(other).map{it.first + it.second}

val PositionL.x:Int get() = get(0)
val PositionL.y:Int get() = get(1)
val PositionL.z:Int get() = get(2)
val PositionL.w:Int get() = get(3)

fun surroundingPositions(dimensions:Int):List<PositionL> =
    (0 until (3.0.pow(dimensions ).toInt())).map{
        (0 until dimensions).map{ n ->
            (it / 3.0.pow(n).toInt() % 3 - 1 )
        }
    }.filter{ d -> !d.all{it == 0}}

fun updateCube(conwayCubes: ConwayCubes, data:List<String>, dimensions:Int) =
    (data.first().indices).forEach{ x -> (0..data.lastIndex).forEach{ y->
        conwayCubes[(0 until dimensions).map{ when (it) {0 -> x 1 -> y else ->0} }] = data[y][x].toState() } }

enum class State(val text:Char) {
    Active('#'), Inactive('.')
}
fun Char.toState() = if (this == State.Active.text) State.Active else State.Inactive

typealias ConwayCubes = MutableMap<PositionL, State>

fun ConwayCubes.activeNeighbours(position:PositionL, surroundingPositions:List<List<Int>>) =
    surroundingPositions.count { surroundingPosition -> get(position + surroundingPosition) == State.Active  }

fun ConwayCubes.newState(position:PositionL,surroundingPositions:List<List<Int>>):State {
    val activeNeighbours = activeNeighbours(position, surroundingPositions)
    return if (get(position) == State.Active)
        if (activeNeighbours ==2 || activeNeighbours == 3) State.Active else State.Inactive
    else
        if (activeNeighbours == 3) State.Active else State.Inactive
}

fun ConwayCubes.repeatCycles(noOfCycles:Int, dimensions: Int):ConwayCubes =
    if (noOfCycles <= 0) this else cycle(dimensions).repeatCycles(noOfCycles -1, dimensions)


//part two

fun ConwayCubes.cycle(dimensions:Int):ConwayCubes {
    val surroundingPositions = surroundingPositions(dimensions)
    val minAndMaxes = (0..(dimensions-1)).map{d -> (keys.minOf { it[d] } -1)..(keys.maxOf { it[d] } +1)}.map{it.toList()}
    val positions = getPositions(minAndMaxes, dimensions)
    val newConwayCubes = toMutableMap()
    positions.forEach { position ->
        newConwayCubes [position] = newState(position, surroundingPositions)
    }
    return newConwayCubes
}

fun getPositions(minAndMaxes: List<List<Int>>, dimensions: Int): List<PositionL> {
    var n = 0
    var combos = minAndMaxes[n++].map { listOf(it) }
    while (n < dimensions) {
        combos = combos.flatMap { first -> minAndMaxes[n].map { second -> first + second } }
        n++
    }
    return combos
}


