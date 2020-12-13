fun parseData(data: List<String>) =
    Pair(data[0].toInt(),
        data[1].split(",").filter{it!="x"}.map{it.toInt()}
    )

fun findBestBus(earliestDepart:Int, busIds:List<Int>):Pair<Int, Int> {
    var delay = 0
    while (true) {
        busIds.forEach { busId ->
            if (busDepartsAtTime(busId, earliestDepart + delay)) return Pair(busId, earliestDepart+ delay)
        }
        delay++
    }
}
fun busDepartsAtTime(busId:Int, time:Int) = (time % busId) == 0

fun partOne(data:List<String>):Pair<Int, Int> = findBestBus(parseData(data).first, parseData(data).second )

data class BusInfo(val busId:Int, val interval:Int)

fun transform(p:Pair<List<BusInfo>, Int>, data:String):Pair<List<BusInfo>,Int> {
    val (listOfBusInfo, busCount) = p
    return if (data == "x") Pair(listOfBusInfo, busCount + 1)
    else Pair(listOfBusInfo + BusInfo(data.toInt(), busCount + 1), busCount + 1)
}
fun parseData2(data:List<String>):Pair<List<BusInfo>,Int> {
    val initial = Pair(listOf(BusInfo(data[1].split(",")[0].toInt(),0)),0)
    return data[1].split(",").drop(1).fold(initial, ::transform)
}

fun partTwo(data:List<String>): Long {
    val (listOfBusInfo, _) = parseData2(data)

    var totalTime =  listOfBusInfo[0].busId.toLong()
    var minTimeincrement = 1L
    for (busInfo in listOfBusInfo) {
        totalTime = nextValidtimeForBus(busInfo, totalTime, minTimeincrement)
        minTimeincrement *= busInfo.busId
    }
    return totalTime
}
fun timeIsValidForBus(busInfo:BusInfo, time:Long ):Boolean = ( (time + busInfo.interval) % busInfo.busId == 0L)

fun nextValidtimeForBus(busInfo:BusInfo, startingTime:Long, timeIncrement:Long ):Long {
    var time = startingTime + timeIncrement
    while (true) {
        if ( timeIsValidForBus(busInfo, time))  return time
        time = time + timeIncrement
    }
}