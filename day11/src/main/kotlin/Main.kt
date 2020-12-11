const val unOccupiedSeat = 'L'
const val occupiedSeat = '#'
const val floor = '.'

typealias Grid = List<String>
val Grid.maxCol get() = first().length - 1
val Grid.maxRow get() = size - 1

data class Position(val col:Int, val row:Int ) {
    fun offset(d:Position, n:Int) = Position(col + d.col * n , row + d.row * n)
    fun onGrid(grid:Grid) = col in 0..grid.maxCol && row in 0..grid.maxRow
}
val allDirections = (-1..1).flatMap{ row -> (-1..1).map{ col -> Position(col,row)}}.filter{it != Position(0,0)}

fun Grid.seatAt(p:Position) = get(p.row)[p.col]

fun Grid.line(position:Position, d:Position):String {
    var line = ""
    var n = 1
    while ( position.offset(d,n).onGrid(this)) {
        line += seatAt(position.offset(d,n))
        if (seatAt(position.offset(d,n)) != floor ) return line else n++
    }
    return line
}

fun Grid.linesOfSeats(position:Position, directions:List<Position>):List<String> =directions.map{ direction -> line(position,direction)}

fun Grid.noOfOccupiedAdjacentSeats(position: Position) = linesOfSeats(position,allDirections).count{it.startsWith(occupiedSeat)}

fun Grid.newStateOfSeat(position:Position, rule:Rule):Char {
    val noOfOccupiedSeats = rule.adjacentOccupiedSeatCalculator(this, position)
    if (seatAt(position) == unOccupiedSeat && noOfOccupiedSeats == 0) return occupiedSeat
    if (seatAt(position) == occupiedSeat && noOfOccupiedSeats > rule.maxAdjacentSeats) return unOccupiedSeat
    return seatAt(position)
}

data class Rule(val maxAdjacentSeats: Int, val adjacentOccupiedSeatCalculator:Grid.(Position)->Int)

val partOneRule = Rule(3,Grid::noOfOccupiedAdjacentSeats)

fun Grid.transformSeats(rule:Rule):Grid = mapIndexed{row, line ->
                                                line.mapIndexed{col, _ ->
                                                        newStateOfSeat(Position(col,row),rule)
                                                }
                                            }.map{it.joinToString("")}

tailrec fun Grid.transformUntilStable(rule:Rule):Grid {
    val transformed = transformSeats(rule)
    return if (transformed == this) this else transformed.transformUntilStable(rule)
}

fun Grid.noOfOccupiedSeats() = sumBy { it.count { char ->  char==occupiedSeat } }


//part two
val partTwoRule = Rule(4, Grid::noOfVisibleOccupiedSeats)

fun Grid.noOfVisibleOccupiedSeats(position:Position) = linesOfSeats(position,allDirections).count(::firstVisibleSeatIsOccupied)
fun firstVisibleSeatIsOccupied(s:String):Boolean {
    var i = 0
    while (i < s.length) {
        if (s[i] == occupiedSeat) return true
        if (s[i] == unOccupiedSeat) return false
        i++
    }
    return false
}

