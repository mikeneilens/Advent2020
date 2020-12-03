
fun String.dataAt(x: Int)= get(x % length)

fun List<String>.dataAt(x:Int, y:Int) = get(y).dataAt(x)

fun Char.isSpace() = (this == '.')

fun Char.isTree() = (this == '#')

fun List<String>.treesEncountered(xMove:Int, yMove:Int, x:Int = 0, result:Long = 0):Long =
    when (true) {
        isEmpty() -> result
        first().dataAt(x).isTree() -> drop(yMove).treesEncountered(xMove, yMove,x + xMove , result + 1)
        else -> drop(yMove).treesEncountered(xMove, yMove, x + xMove, result )
    }
