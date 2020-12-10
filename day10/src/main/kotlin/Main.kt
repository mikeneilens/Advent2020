fun List<Int>.builtInJolt(): Int = (maxOrNull() ?: 0) + 3

fun List<Int>.allJolts() = (listOf(0) + this + builtInJolt()).sorted()

fun List<Int>.createPairs():List<Pair<Int, Int>> = allJolts().zip(allJolts().drop(1))

fun List<Pair<Int,Int>>.findDifferencesOfOneAndThree():Pair<Int, Int>  {
    val differences = map{(j1,j2) -> j2 - j1 }
    return Pair(differences.count{it == 1},differences.count{it == 3} )
}
fun dayOne(jolts:List<Int>) = jolts.createPairs().findDifferencesOfOneAndThree()

fun List<Int>.combos(listSoFar:List<Int> = listOf()):List<List<Int>> {
    if (isEmpty()) return listOf(listSoFar)
    val last = if (listSoFar.isEmpty())  0 else listSoFar.last()
    return take(3).flatMap {next ->
        if (next - last <= 3 && next != last ) filter{it > next  }.combos(listSoFar + next ) else listOf()
    }
}

fun List<Int>.part2(): Long {
    val jolts = sorted() + builtInJolt()
    val pathsToJolt = mutableMapOf(0 to 1L)
    jolts.forEach { jolt ->
        pathsToJolt[jolt] = precedingPaths(pathsToJolt, jolt)
    }
    return pathsToJolt[builtInJolt()] ?: 0
}
fun precedingPaths(pathsToJolt:MutableMap<Int,Long>, jolt:Int)
        = (pathsToJolt[jolt - 1] ?: 0) + (pathsToJolt[jolt - 2] ?: 0) + (pathsToJolt[jolt - 3] ?: 0)




