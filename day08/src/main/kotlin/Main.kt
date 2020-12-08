
var accumulator = 0

data class ProgState(val ndx:Int, val accumulator:Int)

fun nop(state:ProgState, argument:Int):ProgState = ProgState(state.ndx + 1, state.accumulator )
fun acc(state:ProgState, argument:Int):ProgState = ProgState(state.ndx + 1, state.accumulator + argument )
fun jmp(state:ProgState, argument:Int):ProgState = ProgState(state.ndx + argument, state.accumulator)

typealias Rule = (ProgState, Int)->ProgState
typealias Instruction = (ProgState) -> ProgState
typealias Program = Map<Int, String>

val ruleMap = mapOf<String, Rule>(
    "nop" to ::nop,
    "acc" to ::acc,
    "jmp" to ::jmp
)
fun String.parseIntoProgram(): Program = split("\n").mapIndexed{i,v -> Pair(i,v)}.toMap()

fun toInstruction(s:String):Instruction {
    val parts= s.split(" ")
    val rule = ruleMap.getValue(parts[0])
    val argument = parts[1].toInt()
    return {progState -> rule(progState, argument) }
}

fun Program.process( ):Pair<Int, Boolean> {
    var progState = ProgState(0,0)
    val indexesVisited = mutableListOf<Int>()
    while (!indexesVisited.contains(progState.ndx) && get(progState.ndx) != null ) {
        indexesVisited.add(progState.ndx)
        val instruction = toInstruction(getValue(progState.ndx))
        progState = instruction(progState)
    }
    return Pair(progState.accumulator,get(progState.ndx) == null)
}

fun Program.swappables() = toList().sortedBy { it.first }.filter{it.second.contains("jmp") || it.second.contains("nop")}

fun partTwo(string:String):Pair<Int, Boolean> {
    val program = string.parseIntoProgram()
    val jmpOrNOPToSwap = program.swappables()
    var oneToSwap = 0
    while (oneToSwap < jmpOrNOPToSwap.size) {
        val updatableProgram = program.toMutableMap()
        replaceJmpOrNop(updatableProgram, jmpOrNOPToSwap, oneToSwap)
        val (acc, finishedOK) = updatableProgram.process()
        if (finishedOK) return Pair(acc, finishedOK)
        oneToSwap++
    }
    return Pair(-1,false)
}

fun replaceJmpOrNop(program: MutableMap<Int, String>, swappables: List<Pair<Int, String>>, oneToSwap: Int) {
    val swappableIndex = swappables[oneToSwap].first
    val swappableInstruction = swappables[oneToSwap].second
    program[swappableIndex] = if (swappableInstruction.contains("jmp"))
        swappableInstruction.replace("jmp", "nop")
    else
        swappableInstruction.replace("nop", "jmp")
}
