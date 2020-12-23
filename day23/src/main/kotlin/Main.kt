
fun List<Int>.pickCups(start:Int):List<Int>{
    return listOf(get(cycle(start + 1)), get(cycle(start + 2) ), get(cycle(start + 3)))
}

fun cycle(value:Int) = if (value >= 0) value % 9 else (10 + value)

fun List<Int>.destination(start:Int, pickedUpCups:List<Int>, offset:Int = - 1):Int {
    val label = get(start) + offset
    val target = if (label > 0) label else 9 + label
    if (pickedUpCups.contains(target))
        return destination(start,pickedUpCups, offset - 1 )
    else {
        return  target
    }
}

fun List<Int>.processCups(start:Int):Pair<List<Int>, Int> {
    val result = mutableListOf<Int>()
    val pickedUpCups = pickCups(start)
    val destination = destination(start, pickedUpCups)
    var i = 0
    while (i < size) {
        if (!pickedUpCups.contains(get(i))) result.add(get(i))
        if (get(i) == destination) {
            pickedUpCups.forEach { result.add(it) }
        }
        i++
    }
    val nextCup = cycle(result.indexOfFirst{ it == get(start)} + 1)
    return Pair(result, nextCup)
}

fun List<Int>.process(start:Int = 0,times:Int = 10):List<Int> {
    if (times == 0) return this
    val (result, nextStart) = processCups(start)
    return result.process(nextStart, times - 1)
}

fun List<Int>.result():String {
    var s = indexOfFirst{it == 1}
    var result = ""
    (1..8).forEach{ offset ->
        result += get(cycle(s + offset))
    }
    return result
}