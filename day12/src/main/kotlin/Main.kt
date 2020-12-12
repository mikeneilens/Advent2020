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

data class ShipType1 (val position:Position, val orientation:Orientation)
val String.op get() = get(0)
val String.n get() = drop(1).toInt()

fun ShipType1.transform(instruction:String):ShipType1 = when (instruction.op) {
    'N' -> ShipType1(position + Orientation.North.step * instruction.n, orientation)
    'S' -> ShipType1(position + Orientation.South.step * instruction.n, orientation)
    'E' -> ShipType1(position + Orientation.East.step * instruction.n, orientation)
    'W' -> ShipType1(position + Orientation.West.step * instruction.n, orientation)
    'R' -> ShipType1(position, orientation.rotateRight(instruction.n))
    'L' -> ShipType1(position, orientation.rotateLeft(instruction.n))
    'F' -> ShipType1(position + orientation.step * instruction.n, orientation)
    else -> ShipType1(position, orientation)
}

fun partOne(sampleData: List<String>): ShipType1 =
    sampleData.fold(ShipType1(Position(0,0),Orientation.East), ShipType1::transform )

//part two
data class ShipType2 (val position:Position, val orientation:Orientation, val wayPoint:Position)

fun ShipType2.transform(instruction:String):ShipType2 {
    return when (instruction.op) {
        'N' -> ShipType2(position, orientation, wayPoint + Orientation.North.step * instruction.n)
        'S' -> ShipType2(position, orientation, wayPoint + Orientation.South.step * instruction.n)
        'E' -> ShipType2(position, orientation, wayPoint + Orientation.East.step * instruction.n)
        'W' -> ShipType2(position, orientation, wayPoint + Orientation.West.step * instruction.n)
        'R' -> ShipType2(position, orientation, wayPoint.rotateRight(instruction.n))
        'L' -> ShipType2(position, orientation, wayPoint.rotateLeft(instruction.n))
        'F' -> ShipType2(position + wayPoint * instruction.n, orientation, wayPoint )
        else -> ShipType2(position, orientation, wayPoint)
    }
}

fun partTwo(sampleData:List<String>)
        =  sampleData.fold(ShipType2(Position(0,0),Orientation.East, Position(10,-1)),ShipType2::transform)
