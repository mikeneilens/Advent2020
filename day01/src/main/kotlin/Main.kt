
fun Pair<Int,Int>.sumIs2020() = (first+second) == 2020

fun simplePermutation(list:List<Int>):List<Pair<Int,Int>> = list.flatMap{v1 -> list.map{v2 -> Pair(v1,v2)} }.filter{(v1,v2) -> v1 != v2}

fun partOne(list:List<Int>) = simplePermutation(list).first{it.sumIs2020()}

fun Triple<Int,Int,Int>.sumIs2020() = (first+second + third) == 2020

fun simplePermutation3(list:List<Int>)
        = list.flatMap{v1 -> list.flatMap{v2 -> list.map{v3 -> Triple(v1,v2,v3) }} }.filter{(v1,v2,v3) -> (v1 != v2 && v2 != v3 && v1 != v3) }

fun partTwo(list:List<Int>) = simplePermutation3(list).first{it.sumIs2020()}
