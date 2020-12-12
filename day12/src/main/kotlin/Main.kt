data class Position(val x:Int, val y:Int) {
    operator fun plus(other:Position):Position = Position(x + other.x, y + other.y)
    operator fun times(other:Int):Position = Position(x * other, y * other)

    fun rotateRightOnce():Position =  Position(y * -1, x)
    fun rotateRight(d:Int):Position = if (d == 0)  this else rotateRightOnce().rotateRight(d - 90)
    fun rotateLeftOnce():Position =  Position(y , x * -1)
    fun rotateLeft(d:Int):Position = if (d == 0)  this else rotateLeftOnce().rotateLeft(d - 90)
}
enum class Orientation(val step:Position) {
    North(Position(0,-1)),
    South(Position(0,1)),
    East(Position(1,0)),
    West(Position(-1,0));

    fun rotateRightOnce() = when(this) {
        North -> East
        South -> West
        East -> South
        West -> North
    }
    fun rotateRight(d:Int):Orientation = if (d == 0)  this else rotateRightOnce().rotateRight(d - 90)
    fun rotateLeftOnce() = when(this) {
        North -> West
        South -> East
        East -> North
        West -> South
    }
    fun rotateLeft(d:Int):Orientation = if (d == 0)  this else rotateLeftOnce().rotateLeft(d - 90)
}

data class ShipStatus (val position:Position, val orientation:Orientation)
val String.op get() = get(0)
val String.n get() = drop(1).toInt()

fun ShipStatus.transform(instruction:String):ShipStatus = when (instruction.op) {
    'N' -> ShipStatus(position + Orientation.North.step * instruction.n, orientation)
    'S' -> ShipStatus(position + Orientation.South.step * instruction.n, orientation)
    'E' -> ShipStatus(position + Orientation.East.step * instruction.n, orientation)
    'W' -> ShipStatus(position + Orientation.West.step * instruction.n, orientation)
    'R' -> ShipStatus(position, orientation.rotateRight(instruction.n))
    'L' -> ShipStatus(position, orientation.rotateLeft(instruction.n))
    'F' -> ShipStatus(position + orientation.step * instruction.n, orientation)
    else -> ShipStatus(position, orientation)
}

fun <ShipType>finalPosition(shipStatus:ShipType, sampleData1: List<String>, transformer:(ShipType, String)->ShipType): ShipType {
    var newStatus = shipStatus
    sampleData1.forEach{ instruction ->
        newStatus =  transformer(newStatus,instruction)
    }
    return newStatus
}
fun partOne(sampleData1: List<String>): ShipStatus =
    finalPosition(ShipStatus(Position(0,0),Orientation.East), sampleData1, ShipStatus::transform )

//part two
data class ShipStatus2 (val position:Position, val orientation:Orientation, val wayPoint:Position)

fun ShipStatus2.transform(instruction:String):ShipStatus2 {
    return when (instruction.op) {
        'N' -> ShipStatus2(position, orientation, wayPoint + Orientation.North.step * instruction.n)
        'S' -> ShipStatus2(position, orientation, wayPoint + Orientation.South.step * instruction.n)
        'E' -> ShipStatus2(position, orientation, wayPoint + Orientation.East.step * instruction.n)
        'W' -> ShipStatus2(position, orientation, wayPoint + Orientation.West.step * instruction.n)
        'R' -> ShipStatus2(position, orientation, wayPoint.rotateRight(instruction.n))
        'L' -> ShipStatus2(position, orientation, wayPoint.rotateLeft(instruction.n))
        'F' -> ShipStatus2(position + wayPoint * instruction.n, orientation, wayPoint )
        else -> ShipStatus2(position, orientation, wayPoint)
    }
}

fun partTwo(sampleData:List<String>)
        = finalPosition(ShipStatus2(Position(0,0),Orientation.East, Position(10,-1)), sampleData, ShipStatus2::transform)