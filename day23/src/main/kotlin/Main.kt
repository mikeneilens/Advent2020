
fun List<Int>.pickCups(start:Int):List<Int>{
    return listOf(get(cycle(start + 1)), get(cycle(start + 2) ), get(cycle(start + 3)))
}

fun cycle(value:Int) = if (value >= 0) value % 9 else (10 + value)

fun List<Int>.destinationCup(start:Int, pickedUpCups:List<Int>, offset:Int = - 1):Int {
    val cupLabel = get(start) + offset
    val destinationCup = if (cupLabel > 0) cupLabel else size + cupLabel
    return if (pickedUpCups.contains(destinationCup))
        destinationCup(start,pickedUpCups, offset - 1 )
    else {
        destinationCup
    }
}

fun List<Int>.processCups(start:Int):Pair<List<Int>, Int> {
    val newCircle = mutableListOf<Int>()
    val pickedUpCups = pickCups(start)
    val destinationCup = destinationCup(start, pickedUpCups)
    var i = 0
    while (i < size) {
        val cupOnOldCircle = get(i)
        if (!pickedUpCups.contains(cupOnOldCircle)) newCircle.add(cupOnOldCircle)
        if (cupOnOldCircle == destinationCup) {
            pickedUpCups.forEach {cup -> newCircle.add(cup) }
        }
        i++
    }
    val indexOfNextCup = cycle(newCircle.indexOfFirst{ it == get(start)} + 1)
    return Pair(newCircle, indexOfNextCup)
}

fun List<Int>.process(start:Int = 0,times:Int = 10):List<Int> {
    if (times == 0) return this
    val (result, nextStart) = processCups(start)
    return result.process(nextStart, times - 1)
}

fun List<Int>.compacted():String {
    val s = indexOfFirst{it == 1}
    var result = ""
    (1..8).forEach{ offset ->
        result += get(cycle(s + offset))
    }
    return result
}