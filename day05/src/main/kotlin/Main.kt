import java.lang.Math.pow

fun String.binaryToInteger(char:Char = '1'): Int =
    reversed().mapIndexed{index, digit -> if (digit == char) pow(2.0, index.toDouble()).toInt() else 0 }.sum()


fun String.toRow() = dropLast(3).binaryToInteger('B')
fun String.toColumn() = takeLast(3).binaryToInteger('R')
fun String.toSeatId() = toRow() * 8 +  toColumn()

fun String.allSeats() = split("\n").map{it.toSeatId()}.sorted()
fun String.maxSeatId() = allSeats().sorted().last()

fun List<Int>.findMissingSeat() = windowed(2,1).first{ seatIsMissing(it)}.first() + 1

fun seatIsMissing(seats:List<Int>) = (seats[0] + 1) != seats[1]

fun String.findMissingSeat() = allSeats().findMissingSeat()