
fun String.toListOfLongs():List<Long> = split("\n").map{it.toLong()}

fun combinedValuesEqualToLast(numbers:List<Long>):List<Pair<Long,Long>> {
    val results= mutableListOf<Pair<Long,Long>>()
    numbers.forEachIndexed { index1, number ->
        var index2 = index1 + 1
        while (index2 < numbers.size) {
            if (number + numbers[index2] == numbers.last()) results.add(Pair(number, numbers[index2]))
            index2++
        }
    }
    return results
}

fun blockContainsError(numbers:List<Long>):Boolean = combinedValuesEqualToLast(numbers).isEmpty()

fun findFirstErrorIn(numbers:List<Long>, size:Int):Long? = numbers.windowed(size,1).first(::blockContainsError).last()

//Part two
fun sumUntilGreaterOrEqual(numbers:List<Long>, target:Long, startNdx:Int = 0):Pair<Int, Int>? {
    var total = 0L
    var endNdx = startNdx
    while (total < target && endNdx < numbers.size) {
        total += numbers[endNdx]
        if (total == target) return Pair(startNdx, endNdx)
        endNdx++
    }
    return null
}
fun minAndMaxInRange(numbers:List<Long>, start:Int, end:Int):Pair<Long, Long> =
    Pair(numbers.subList(start,end).minOrNull() ?: 0 , numbers.subList(start,end).maxOrNull() ?:0)


fun findContiguousItemsMatching(numbers:List<Long>, target:Long):Pair<Long, Long> {
    val (firstIndex,_) = numbers.mapIndexed{ i, v -> Pair(i,v)}
        .first { sumUntilGreaterOrEqual(numbers, target, it.first) != null}
    val (start,end) = sumUntilGreaterOrEqual(numbers, target, firstIndex) ?: Pair(-1,-1)
    return minAndMaxInRange(numbers, start,end)
}
