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

fun parseData2(data:List<String>):List<BusInfo> = data[1]
    .split(",")
    .mapIndexed{i,v -> Pair(i,v)}
    .filter { it.second != "x"}
    .map{BusInfo(it.second.toInt(), it.first)}

fun partTwo(data:List<String>): Long {
    val listOfBusInfo  = parseData2(data)
    return totalTime(listOfBusInfo)
}

tailrec fun totalTime(listOfBusInfo:List<BusInfo>, timeSoFar:Long = 0L, increment:Long = 1L):Long {
    if (listOfBusInfo.isEmpty()) return timeSoFar
    val bus = listOfBusInfo.first()
    val timeForBus = nextValidTimeForBus(bus, timeSoFar,increment)
    return totalTime(listOfBusInfo.drop(1), timeForBus, increment * bus.busId ) //the increment is the tricky bit to work out
}

fun timeIsValidForBus(busInfo:BusInfo, time:Long ):Boolean = ( (time + busInfo.interval) % busInfo.busId == 0L)

fun nextValidTimeForBus(busInfo:BusInfo, startingTime:Long, timeIncrement:Long ):Long {
    var time = startingTime + timeIncrement
    while (true) {
        if ( timeIsValidForBus(busInfo, time))  return time
        time += timeIncrement
    }
}