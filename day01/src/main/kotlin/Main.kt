
fun sumIs2020(pair:Pair<Int,Int>) = (pair.first+pair.second) == 2020

fun simplePermutation(list:List<Int>):List<Pair<Int,Int>> = list.flatMap{v1 -> list.map{v2 -> Pair(v1,v2)} }.filter{(v1,v2) -> v1 != v2}

fun partOne(list:List<Int>) = simplePermutation(list).first(::sumIs2020)

fun sumIs2020(triple:Triple<Int,Int,Int>) = (triple.first + triple.second + triple.third) == 2020

//For Part two only combine values that will result in a total not greater than the target. Makes the list generated much smaller.
fun simplePermutation3(list:List<Int>)
        = list.flatMap{ v1 -> list.filter { it + v1 <= 2020 }.flatMap{ v2 -> list.filter { it + v1 + v2 <= 2020 }.map{v3 -> Triple(v1,v2,v3) }} }.filter{(v1,v2,v3) -> (v1 != v2 && v2 != v3 && v1 != v3) }

fun partTwo(list:List<Int>) = simplePermutation3(list).first(::sumIs2020)

