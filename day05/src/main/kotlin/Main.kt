import java.lang.Math.pow

fun String.binaryToInteger(chars:String = "1") =
    reversed().mapIndexed{index, digit -> if ( chars.contains(digit)) pow(2, index) else 0 }.sum()

fun pow(a:Int, b:Int) = pow(a.toDouble(), b.toDouble()).toInt()

fun toSeatId(s:String) = s.binaryToInteger("BR")

fun String.allSeats() = split("\n").map(::toSeatId).sorted()
fun String.maxSeatId() = allSeats().last()

fun List<Int>.theMissingSeat() = zip(drop(1)).first(::nonConsecutiveSeats).first + 1

fun nonConsecutiveSeats(seats:Pair<Int,Int>) = (seats.first + 1 != seats.second)

fun String.theMissingSeat() = allSeats().theMissingSeat()