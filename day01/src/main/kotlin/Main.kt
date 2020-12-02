
fun sumIs2020(pair:Pair<Int,Int>) = (pair.first+pair.second) == 2020

fun simplePermutation(list:List<Int>):List<Pair<Int,Int>> = list.flatMap{v1 -> list.map{v2 -> Pair(v1,v2)} }.filter{(v1,v2) -> v1 != v2}

fun partOne(list:List<Int>) = simplePermutation(list).first(::sumIs2020)

fun sumIs2020(triple:Triple<Int,Int,Int>) = (triple.first + triple.second + triple.third) == 2020

//For Part two only combine values that will result in a total not greater than the target. Makes the list generated much smaller.
fun simplePermutation3(list:List<Int>)
        = list.flatMap{ v1 -> list.filter { it + v1 <= 2020 }.flatMap{ v2 -> list.filter { it + v1 + v2 <= 2020 }.map{v3 -> Triple(v1,v2,v3) }} }.filter{(v1,v2,v3) -> (v1 != v2 && v2 != v3 && v1 != v3) }

fun partTwo(list:List<Int>) = simplePermutation3(list).first(::sumIs2020)

//======== below are the fastest versions I could make ======================//

fun optimisedPartOne(list:List<Int>):Pair<Int,Int> {
    val sortedList = list.sorted()
    var v1 = 0
    var cycles = 0
    while (v1 < sortedList.size) {
        var v2 = 0
        while (v2 < sortedList.size) {
            cycles++
            if ((sortedList[v1] + sortedList[v2]) == 2020) {
                println("cycles $cycles, v1 $v1, v2 $v2")
                return Pair(sortedList[v1], sortedList[v2])
            }
            v2++
        }
        v1++
    }
    return Pair(0, 0)
}
fun optimisedPartTwo(list:List<Int>):Triple<Int,Int, Int> {
    val sortedList = list.sorted()
    var v1 = 0
    var cycles = 0
    while (v1 < sortedList.size) {
        var v2 = 0
        while (v2 < sortedList.size && (sortedList[v1] + sortedList[v2]) < 2020 ) {
            var v3 = 0
            while (v3 < sortedList.size) {
                cycles++
                if ((sortedList[v1] + sortedList[v2] + sortedList[v3]) == 2020) {
                    println("cycles $cycles, v1 $v1, v2 $v2, v3 $v3")
                    return Triple(sortedList[v1], sortedList[v2], sortedList[v3])
                }
                v3++
            }
            v2++
        }
        v1++
    }
    return Triple(0,0,0)
}

// === using a sequence ===//
fun partOneSequence(list:List<Int>):Pair<Int,Int>{
    val sequence = list.sorted().asSequence()
    return sequence.flatMap{v1 -> sequence.map{v2 -> Pair(v1,v2)} }.first {  (it.first + it.second) == 2020  }
}
fun partTwoSequence(list:List<Int>):Triple<Int,Int, Int>{
    val sequence = list.sorted().asSequence()
    return   sequence.flatMap{ v1 -> sequence.takeWhile { it + v1 <= 2020 }.flatMap{ v2 -> sequence.map{v3 -> Triple(v1,v2,v3) }} }.first{(it.first + it.second + it.third) == 2020}
}
