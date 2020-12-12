data class Position(val x:Int, val y:Int) {
    operator fun plus(other:Position):Position = Position(x + other.x, y + other.y)
    operator fun times(other:Int):Position = Position(x * other, y * other)

    private fun rotateRightOnce():Position =  Position(y * -1, x)
    infix fun rotate(d:Int):Position = if (d == 0)  this else rotateRightOnce().rotate(d - 90)
}
enum class Orientation(val step:Position) {
    North(Position(0,-1)),
    South(Position(0,1)),
    East(Position(1,0)),
    West(Position(-1,0));

    private fun rotateRightOnce() = when(this) {
        North -> East
        South -> West
        East -> South
        West -> North
    }
   infix fun rotate(d:Int):Orientation = if (d == 0)  this else rotateRightOnce().rotate(d - 90)
}

data class ShipType1 (val position:Position, val orientation:Orientation)
val String.op get() = get(0)
val String.n get() = drop(1).toInt()

val shipType1Instructions = mapOf(
    'N' to {s:ShipType1, n:Int -> ShipType1(s.position + Orientation.North.step * n, s.orientation)},
    'S' to {s:ShipType1, n:Int -> ShipType1(s.position + Orientation.South.step * n, s.orientation)},
    'E' to {s:ShipType1, n:Int -> ShipType1(s.position + Orientation.East.step * n, s.orientation)},
    'W' to {s:ShipType1, n:Int -> ShipType1(s.position + Orientation.West.step * n, s.orientation)},
    'R' to {s:ShipType1, n:Int -> ShipType1(s.position, s.orientation rotate n )},
    'L' to {s:ShipType1, n:Int -> ShipType1(s.position, s.orientation rotate(360 - n) )},
    'F' to {s:ShipType1, n:Int -> ShipType1(s.position + s.orientation.step * n, s.orientation)}
)

fun ShipType1.transform(instruction: String):ShipType1 = shipType1Instructions.getValue(instruction.op)(this, instruction.n)

fun partOne(instructions: List<String>): ShipType1 =
    instructions.fold(ShipType1(Position(0,0),Orientation.East), ShipType1::transform )

//part two
data class ShipType2 (val position:Position, val wayPoint:Position)

val shipType2Instructions = mapOf(
    'N' to {s:ShipType2, n:Int -> ShipType2(s.position, s.wayPoint + Orientation.North.step * n)},
    'S' to {s:ShipType2, n:Int -> ShipType2(s.position, s.wayPoint + Orientation.South.step * n)},
    'E' to {s:ShipType2, n:Int -> ShipType2(s.position, s.wayPoint + Orientation.East.step * n)},
    'W' to {s:ShipType2, n:Int -> ShipType2(s.position, s.wayPoint + Orientation.West.step * n)},
    'R' to {s:ShipType2, n:Int -> ShipType2(s.position, s.wayPoint rotate n)},
    'L' to {s:ShipType2, n:Int -> ShipType2(s.position, s.wayPoint rotate (360 - n))},
    'F' to {s:ShipType2, n:Int -> ShipType2(s.position + s.wayPoint * n, s.wayPoint)}
)

fun ShipType2.transform(instruction: String):ShipType2 = shipType2Instructions.getValue(instruction.op)(this, instruction.n)

fun partTwo(instructions:List<String>)
        =  instructions.fold(ShipType2(Position(0,0),Position(10,-1)),ShipType2::transform)

class DSL(var ship:ShipType2 = ShipType2(Position(0,0),Position(0,0)) ) {
    fun N(n:Int):ShipType2 { ship = shipType2Instructions.getValue('N')(ship,n);return ship }
    fun S(n:Int):ShipType2 { ship = shipType2Instructions.getValue('S')(ship,n);return ship }
    fun E(n:Int):ShipType2 { ship = shipType2Instructions.getValue('E')(ship,n);return ship }
    fun W(n:Int):ShipType2 { ship = shipType2Instructions.getValue('W')(ship,n);return ship }
    fun R(n:Int):ShipType2 { ship = shipType2Instructions.getValue('R')(ship,n);return ship }
    fun L(n:Int):ShipType2 { ship = shipType2Instructions.getValue('L')(ship,n);return ship }
    fun F(n:Int):ShipType2 { ship = shipType2Instructions.getValue('F')(ship,n);return ship }

    fun execute(input:DSL.()->ShipType2):ShipType2 = input()
}

