
fun nextNumber(numbers:List<Int>):Int {
    val lastNumber = numbers.last()
    val highestIndex = numbers.dropLast(1).lastIndexOf(lastNumber)
    return if (highestIndex == -1)  0
    else numbers.size - highestIndex - 1
}

tailrec fun numberAt(i: Int, numbers: List<Int>): Int {
    return if (numbers.size >= i ) {println(numbers);   numbers[i-1]}
    else numberAt(i, numbers + nextNumber(numbers) )
}

//part two
val spokenValues = mutableMapOf<Int,Int>()

fun updateMapAndReturnNewValue(lastValue:Int, position:Int, spokenValues:MutableMap<Int,Int>):Int {
    val newValue = (spokenValues[lastValue] ?: position)
    spokenValues[lastValue] = position
    return position - newValue
}
fun process(lastValue:Int, start:Int, finish:Int, spokenValues:MutableMap<Int,Int>):Int {
    var value = lastValue
    for (position in start..finish) {
        value = updateMapAndReturnNewValue(value, position, spokenValues)
    }
    return value
}