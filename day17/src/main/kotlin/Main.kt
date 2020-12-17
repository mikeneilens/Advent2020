import javafx.geometry.Pos

data class Position(val x:Int, val y:Int, val z:Int) {
    operator fun plus(other:Position)  = Position(x + other.x, y + other.y, z + other.z)
}

val surroundingPositions:List<Position> = (-1..1).flatMap{ x-> (-1..1).flatMap{ y -> (-1..1).map{z -> Position(x,y,z)}} }.filter{it != Position(0,0,0)}

enum class State(val text:Char) {
    active('#'), inactive('.')
}
fun Char.toState() = if (this == '#') State.active else State.inactive

typealias ConwayCubes = MutableMap<Position, State>

fun ConwayCubes.activeNeighbours(position:Position) =
    surroundingPositions.count { surroundingPosition -> get(position + surroundingPosition) == State.active  }

fun updateCube(conwayCubes: ConwayCubes, data:List<String>) =
    (0..(data.first().length - 1)).forEach{x -> (0..data.lastIndex).forEach{ y-> conwayCubes[Position(x,y,0)] = data[y][x].toState() } }

fun ConwayCubes.newState(position:Position):State {
    val activeNeighbouts = activeNeighbours(position)
    if (get(position) == State.active)
        if (activeNeighbouts ==2 || activeNeighbouts == 3) return State.active else return State.inactive
    else
        if (activeNeighbouts == 3) return State.active else return State.inactive

}

fun ConwayCubes.cycle():ConwayCubes {
    val newConwayCubes = toMutableMap()
    val minZ = keys.minOf { it.z } - 1
    val maxZ = keys.maxOf { it.z } + 1
    val minX = keys.minOf { it.x } - 1
    val maxX = keys.maxOf { it.x } + 1
    val minY = keys.minOf { it.y } - 1
    val maxY = keys.maxOf { it.y } + 1
    (minZ..maxZ).forEach { z ->
        (minY..maxY).forEach {y->
            (minX..maxX).forEach { x ->
                newConwayCubes [Position(x,y,z)] = newState(Position(x,y,z))
            }
        }
    }
    return newConwayCubes
}

fun ConwayCubes.repeatCycles(noOfCylces:Int):ConwayCubes =
    if (noOfCylces <= 0) this else cycle().repeatCycles(noOfCylces -1)


fun ConwayCubes.print() {
    val minZ = keys.minOf { it.z }
    val maxZ = keys.maxOf { it.z }
    val minX = keys.minOf { it.x }
    val maxX = keys.maxOf { it.x }
    val minY = keys.minOf { it.y }
    val maxY = keys.maxOf { it.y }
    (minZ..maxZ).forEach { z ->
        println("z=$z")
        (minY..maxY).forEach {y->
            var line = ""
            (minX..maxX).forEach { x ->
                line += get(Position(x,y,z))?.let{it.text} ?: " "
            }
            println(line)
        }
    }
}

//part two
data class PositionW(val x:Int, val y:Int, val z:Int, val w:Int) {
    operator fun plus(other:PositionW)  = PositionW(x + other.x, y + other.y, z + other.z, w + other.w)
}

val surroundingPositionWs:List<PositionW> = (-1..1).flatMap{ x-> (-1..1).flatMap{ y -> (-1..1).flatMap{z -> (-1..1).map{ w -> PositionW(x,y,z,w)}}} }.filter{it != PositionW(0,0,0, 0)}

typealias ConwayCubesW = MutableMap<PositionW, State>

fun ConwayCubesW.activeNeighbours(position:PositionW) =
    surroundingPositionWs.count { surroundingPosition -> get(position + surroundingPosition) == State.active  }

fun updateCubeW(conwayCubesW: ConwayCubesW, data:List<String>) =
    (0..(data.first().length - 1)).forEach{x -> (0..data.lastIndex).forEach{ y-> conwayCubesW[PositionW(x,y,0,0)] = data[y][x].toState() } }

fun ConwayCubesW.newState(position:PositionW):State {
    val activeNeighbouts = activeNeighbours(position)
    if (get(position) == State.active)
        if (activeNeighbouts ==2 || activeNeighbouts == 3) return State.active else return State.inactive
    else
        if (activeNeighbouts == 3) return State.active else return State.inactive

}

fun ConwayCubesW.cycle2():ConwayCubesW {
    val newConwayCubes = toMutableMap()
    val minZ = keys.minOf { it.z } - 1
    val maxZ = keys.maxOf { it.z } + 1
    val minX = keys.minOf { it.x } - 1
    val maxX = keys.maxOf { it.x } + 1
    val minY = keys.minOf { it.y } - 1
    val maxY = keys.maxOf { it.y } + 1
    val minW = keys.minOf { it.w } - 1
    val maxW = keys.maxOf { it.w } + 1
    (minW..maxW).forEach{ w ->
        (minZ..maxZ).forEach { z ->
            (minY..maxY).forEach {y->
                (minX..maxX).forEach { x ->
                    newConwayCubes [PositionW(x,y,z,w)] = newState(PositionW(x,y,z,w))
                }
            }
        }
    }
    return newConwayCubes
}

fun ConwayCubesW.repeatCycles2(noOfCylces:Int):ConwayCubesW =
    if (noOfCylces <= 0) this else cycle2().repeatCycles2(noOfCylces -1)

