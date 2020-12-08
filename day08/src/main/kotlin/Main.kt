
data class ProgramState(val line:Int, val accumulator:Int)

val ruleMap = mapOf(
    "nop" to { state:ProgramState, _:Int -> ProgramState(state.line + 1, state.accumulator )},
    "acc" to { state:ProgramState, argument:Int -> ProgramState(state.line + 1, state.accumulator + argument)},
    "jmp" to { state:ProgramState, argument:Int -> ProgramState(state.line + argument, state.accumulator)}
)

data class Instruction (val text:String, var noOfExecutions:Int = 0) {

    private fun ruleToExecute(): (ProgramState) ->ProgramState {
        val parts = text.split(" ")
        val rule = ruleMap.getValue(parts[0])
        val argument = parts[1].toInt()
        return { prodState -> rule(prodState, argument) }
    }

    fun execute(programState:ProgramState):ProgramState {
        noOfExecutions++
        return ruleToExecute()(programState)
    }
}
typealias Program = Map<Int, Instruction>

fun String.parseIntoProgram(): Program = split("\n").mapIndexed{i,v -> Pair(i,Instruction(v))}.toMap()

fun Program.process( ):Pair<Int, Boolean> {
    var programState = ProgramState(0,0)
    while ( !isComplete(programState) ) {
        val instruction = getValue(programState.line)
        programState = instruction.execute(programState)
    }
    return Pair(programState.accumulator,get(programState.line) == null)
}

fun Program.isComplete(programState:ProgramState) = get(programState.line) == null || getValue(programState.line).noOfExecutions > 0
fun Program.reset() = forEach { _, v -> v.noOfExecutions = 0 }

fun Program.instructionsToSwap() = toList()
    .sortedBy(::line)
    .filter(::containsJmpOrNop)

fun line(lineAndInstruction:Pair<Int, Instruction>) = lineAndInstruction.first
fun containsJmpOrNop(lineAndInstruction:Pair<Int, Instruction>)= lineAndInstruction.second.text.contains("jmp") || lineAndInstruction.second.text.contains("nop")

fun partTwo(string:String):Pair<Int, Boolean> {
    val program = string.parseIntoProgram()
    val instructionsToSwap = program.instructionsToSwap()
    var oneToSwap = 0
    while (oneToSwap < instructionsToSwap.size) {
        val (accumulator, finishedOK) = program.replaceInstruction(instructionsToSwap, oneToSwap++).process()
        if (finishedOK) return Pair(accumulator, finishedOK)
        program.reset()
    }
    return Pair(-1,false)
}

fun Program.replaceInstruction(instructionsToSwap: List<Pair<Int, Instruction>>, oneToSwap: Int):Program {
    val updatableProgram = toMutableMap()
    val swappableIndex = instructionsToSwap[oneToSwap].first
    val swappableInstruction = instructionsToSwap[oneToSwap].second
    val newText = if (swappableInstruction.text.contains("jmp"))
        swappableInstruction.text.replace("jmp", "nop")
    else
        swappableInstruction.text.replace("nop", "jmp")
    updatableProgram[swappableIndex] = Instruction(newText, swappableInstruction.noOfExecutions)
    return updatableProgram
}
