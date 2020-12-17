import kotlin.math.pow

typealias ConwayCubes = MutableMap<Position, State>

typealias Position = List<Int>
operator fun Position.plus(other:Position) = this.zip(other).map{it.first + it.second}

fun surroundingPositions(dimensions:Int):List<Position> =
    (0 until (3.0.pow(dimensions ).toInt())).map{
        (0 until dimensions).map{ n ->
            (it / 3.0.pow(n).toInt() % 3 - 1 )
        }
    }.filter{ d -> !d.all{it == 0}}

fun createConwayCubes(data:List<String>, dimensions:Int):ConwayCubes{
    val conwayCubes:ConwayCubes = mutableMapOf()
    (data.first().indices).forEach{ x -> (0..data.lastIndex).forEach{ y->
        conwayCubes[(0 until dimensions).map{ when (it) {0 -> x 1 -> y else ->0} }] = data[y][x].toState() } }
    return conwayCubes
}

enum class State(val text:Char) {
    Active('#'), Inactive('.')
}
fun Char.toState() = if (this == State.Active.text) State.Active else State.Inactive

fun ConwayCubes.activeNeighbours(position:Position, surroundingPositions:List<List<Int>>) =
    surroundingPositions.count { surroundingPosition -> get(position + surroundingPosition) == State.Active  }

fun ConwayCubes.newState(position:Position, surroundingPositions:List<List<Int>>):State {
    val activeNeighbours = activeNeighbours(position, surroundingPositions)
    return if (get(position) == State.Active)
        if (activeNeighbours ==2 || activeNeighbours == 3) State.Active else State.Inactive
    else
        if (activeNeighbours == 3) State.Active else State.Inactive
}

fun ConwayCubes.repeatCycles(noOfCycles:Int, dimensions: Int):ConwayCubes =
    if (noOfCycles <= 0) this else cycle(dimensions).repeatCycles(noOfCycles -1, dimensions)

fun ConwayCubes.cycle(dimensions:Int):ConwayCubes {
    val surroundingPositions = surroundingPositions(dimensions)
    val minAndMaxes = (0 until dimensions).map{d -> (keys.minOf { it[d] } -1)..(keys.maxOf { it[d] } +1)}.map{it.toList()}
    val positions = getPositions(minAndMaxes)
    val newConwayCubes = toMutableMap()
    positions.forEach { position ->
        newConwayCubes [position] = newState(position, surroundingPositions)
    }
    return newConwayCubes
}

fun getPositions(minAndMaxes: List<List<Int>>): List<Position> =
    minAndMaxes.foldIndexed(listOf()){ index, result, minAndMax ->
        if (index == 0) minAndMax.map { listOf(it) }
        else result.flatMap { first -> minAndMax.map { second -> first + second } }
    }

fun List<String>.process(noOfCycles:Int,dimensions:Int) =
    createConwayCubes(this,dimensions).repeatCycles(noOfCycles, dimensions)

