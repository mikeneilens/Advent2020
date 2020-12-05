import java.lang.Math.pow

fun String.binaryToInteger(char:Char = '1'): Int =
    reversed().mapIndexed{index, digit -> if (digit == char) pow(2.0, index.toDouble()).toInt() else 0 }.sum()


fun String.toRow() = dropLast(3).binaryToInteger('B')
fun String.toColumn() = takeLast(3).binaryToInteger('R')
fun String.toSeatId() = toRow() * 8 +  toColumn()

fun String.allSeats() = split("\n").map{it.toSeatId()}.sorted()
fun String.maxSeatId() = allSeats().last()

fun List<Int>.findMissingSeat() = zip(drop(1)).first{ it.first + 1 != it.second}.first + 1

fun String.findMissingSeat() = allSeats().findMissingSeat()