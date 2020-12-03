
fun String.dataAt(x: Int)= get(x % length)

fun Char.isATree() = (this == '#')

fun List<String>.treesEncountered(xMove:Int, yMove:Int, x:Int = 0, noOfTrees:Long = 0):Long =
    when (true) {
        isEmpty() -> noOfTrees
        first().dataAt(x).isATree() -> drop(yMove).treesEncountered(xMove, yMove,x + xMove , noOfTrees + 1)
        else -> drop(yMove).treesEncountered(xMove, yMove, x + xMove, noOfTrees )
    }

fun List<String>.treesEncountered(routes:List<Pair<Int,Int>>) = routes.map{ (x,y) -> treesEncountered(x,y)  }