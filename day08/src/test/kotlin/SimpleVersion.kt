//alternative, just using a string array.
fun MutableList<String>.compute():Pair<Int,Boolean> {
    var line = 0
    var accumulator = 0
    while (line < size) {
        val instruction = get(line)
        if (get(line).isEmpty()) return Pair(accumulator, false)
        this.set(line,"")
        val op = instruction.split(" ")[0]
        val arg = instruction.split(" ")[1].toInt()
        when (op) {
            "acc" -> {accumulator += arg; line += 1}
            "jmp" -> line += arg
            "nop" -> line += 1
        }
    }
    return Pair(accumulator, true)
}

fun part2b(data:String):Pair<Int, Boolean> {
    var n = 0
    while ( n < data.split("\n").size ) {
        val program = data.replaceNthJmpOrNop(n++).split("\n").toMutableList()
        val (accumulator, finishedOK) = program.compute()
        if (finishedOK) return Pair(accumulator, finishedOK)
    }
    return Pair(-1,false)
}