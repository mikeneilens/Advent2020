fun main() = 0

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

fun ShipStatus.transform(instruction:String):ShipStatus {
    val op = instruction[0]
    val n = instruction.drop(1).toInt()
    return when (op) {
        'N' -> ShipStatus(position + Orientation.North.step * n, orientation)
        'S' -> ShipStatus(position + Orientation.South.step * n, orientation)
        'E' -> ShipStatus(position + Orientation.East.step * n, orientation)
        'W' -> ShipStatus(position + Orientation.West.step * n, orientation)
        'R' -> ShipStatus(position, orientation.rotateRight(n))
        'L' -> ShipStatus(position, orientation.rotateLeft(n))
        'F' -> ShipStatus(position + orientation.step * n, orientation)
        else -> ShipStatus(position, orientation)
    }
}

fun finalPosition(sampleData1: List<String>): ShipStatus {
    var shipStatus = ShipStatus(Position(0,0),Orientation.East)
    sampleData1.forEach{ instruction ->
        shipStatus = shipStatus.transform(instruction)
    }
    return shipStatus
}

//part two
data class ShipStatus2 (val position:Position, val orientation:Orientation, val wayPoint:Position)
fun ShipStatus2.transform(instruction:String):ShipStatus2 {
    val op = instruction[0]
    val n = instruction.drop(1).toInt()
    return when (op) {
        'N' -> ShipStatus2(position, orientation, wayPoint + Orientation.North.step * n)
        'S' -> ShipStatus2(position, orientation, wayPoint + Orientation.South.step * n)
        'E' -> ShipStatus2(position, orientation, wayPoint + Orientation.East.step * n)
        'W' -> ShipStatus2(position, orientation, wayPoint + Orientation.West.step * n)
        'R' -> ShipStatus2(position, orientation, wayPoint.rotateRight(n))
        'L' -> ShipStatus2(position, orientation, wayPoint.rotateLeft(n))
        'F' -> ShipStatus2(position + wayPoint * n, orientation, wayPoint )
        else -> ShipStatus2(position, orientation, wayPoint)
    }
}

fun finalPosition2(sampleData1: List<String>): ShipStatus2 {
    var shipStatus = ShipStatus2(Position(0,0),Orientation.East, Position(10,-1))
    sampleData1.forEach{ instruction ->
        shipStatus = shipStatus.transform(instruction)
    }
    return shipStatus
}