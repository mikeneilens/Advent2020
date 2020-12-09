
fun String.toListOfLongs():List<Long> = split("\n").map{it.toLong()}

fun combinedValuesEqualToLast(l:List<Long>):List<Pair<Long,Long>> {
    val results= mutableListOf<Pair<Long,Long>>()
    l.forEachIndexed { index1,v ->
        var index2 = index1 + 1
        while (index2 < l.size) {
            if (v + l[index2] == l.last()) results.add(Pair(v, l[index2]))
            index2++
        }
    }
    return results
}

fun blockContainsError(l:List<Long>):Boolean = combinedValuesEqualToLast(l).isEmpty()

fun checkForError(l:List<Long>,size:Int):Long? = l.windowed(size,1).first(::blockContainsError).last()

//Part two
fun sumUntilGreaterOrEqual(l:List<Long>, target:Long, start:Int = 0):Pair<Int, Int>? {
    var total = 0L
    var ndx = start
    while (total < target) {
        total += l[ndx]
        l.subList(start,ndx).minOrNull()
        if (total == target) return Pair(start , ndx)
        ndx++
    }
    return null
}
fun minAndMaxInRange(l:List<Long>, min:Int, max:Int):Pair<Long, Long> =
    Pair(l.subList(min,max).minOrNull() ?: 0 , l.subList(min,max).maxOrNull() ?:0)


fun findContiguousItemsMatching(l:List<Long>, target:Long):Pair<Long, Long> {
    val (firstIndex,_) = l.mapIndexed{i,v -> Pair(i,v)}
        .first { sumUntilGreaterOrEqual(l, target, it.first) != null}
    val (minNdx,maxNdx) = sumUntilGreaterOrEqual(l, target, firstIndex) ?: Pair(-1,-1)
    return minAndMaxInRange(l, minNdx,maxNdx)
}
