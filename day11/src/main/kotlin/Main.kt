const val unOccupiedSeat = 'L'
const val occupiedSeat = '#'

typealias Grid = List<String>
fun Grid.seatAt(col:Int,row:Int) = get(row)[col]
val Grid.maxCol get() = first().length - 1
val Grid.maxRow get() = size - 1

fun Grid.adjacentSeats(col:Int, row:Int):List<String> {
    val directions = listOf(Pair(0,1),Pair(0,-1),Pair(1,0),Pair(-1,0),Pair(-1,-1),Pair(-1,1),Pair(1,-1),Pair(1,1))
    return directions.map{line(col,row,it)}
}
fun Grid.noOfOccupiedAdjacentSeats(col:Int, row:Int) = adjacentSeats(col,row).count{it.startsWith(occupiedSeat)}

fun Grid.line(col:Int, row:Int, direction:Pair<Int,Int>):String {
    var line = ""
    var c = col + direction.first
    var r = row + direction.second
    while ( c in 0..maxCol && r in 0..maxRow) {
        line += seatAt(c,r)
        c += direction.first
        r += direction.second
    }
    return line
}
fun Grid.newStateOfSeat(col:Int,row:Int, maxAdjacentSeats:Int, calcAdjacentOccupiedSeats:Grid.(Int,Int)->Int):Char {
    val noOfOccupiedSeats = calcAdjacentOccupiedSeats(col, row)
    if (seatAt(col,row) == unOccupiedSeat && noOfOccupiedSeats == 0) return occupiedSeat
    if (seatAt(col,row) == occupiedSeat && noOfOccupiedSeats > maxAdjacentSeats) return unOccupiedSeat
    return seatAt(col,row)
}

fun Grid.transformSeats(maxAdjacentSeats:Int, calcAdjacentOccupiedSeats:Grid.(Int,Int)->Int):Grid = mapIndexed{row, line ->
                                                line.mapIndexed{col, _ ->
                                                        newStateOfSeat(col,row,maxAdjacentSeats,calcAdjacentOccupiedSeats)
                                                }
                                            }.map{it.joinToString("")}

tailrec fun Grid.transformUntilStable():Grid {
    val transformed = transformSeats(3,Grid::noOfOccupiedAdjacentSeats)
    return if (transformed == this) this else transformed.transformUntilStable()
}
fun Grid.noOfOccupiedSeats() = sumBy { it.count { char ->  char==occupiedSeat } }

//part two

fun Grid.noOfVisibleOccupiedAdjacentSeats(col:Int, row:Int) = adjacentSeats(col,row).count(::firstVisibleSeatIsOccupied)
fun firstVisibleSeatIsOccupied(s:String):Boolean {
    var i = 0
    while (i < s.length) {
        if (s[i] == occupiedSeat) return true
        if (s[i] == unOccupiedSeat) return false
        i++
    }
    return false
}

tailrec fun Grid.transformUntilStable2():Grid {
    val transformed = transformSeats(4, Grid::noOfVisibleOccupiedAdjacentSeats)
    return if (transformed == this) this else transformed.transformUntilStable2()
}
