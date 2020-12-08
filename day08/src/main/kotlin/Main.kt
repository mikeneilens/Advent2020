
data class ProgramState(val line:Int, val accumulator:Int)
typealias Program = Map<Int, Instruction>

sealed class Operator (val fn:(ProgramState, Int)->ProgramState, var argument:Int) {}
class NOP(
    fn:(ProgramState, Int)->ProgramState = {state, _ -> ProgramState(state.line + 1, state.accumulator )},
    argument:Int):Operator(fn,argument) {
    fun toJMP() = JMP(argument = argument)
}
class ACC(
    fn:(ProgramState, Int)->ProgramState = {state, argument -> ProgramState(state.line + 1, state.accumulator + argument)},
    argument:Int):Operator(fn,argument)
class JMP(
    fn:(ProgramState, Int)->ProgramState = {state, argument -> ProgramState(state.line + argument, state.accumulator)},
    argument:Int):Operator(fn,argument) {
    fun toNOP() =NOP(argument = argument)
}

data class Instruction (val operator:Operator, private var noOfExecutions:Int = 0) {

    private fun operation(): (ProgramState) ->ProgramState = { programState -> noOfExecutions++;operator.fn(programState, operator.argument) }

    fun execute(programState:ProgramState):ProgramState = operation()(programState)

    fun changeToJmp() = Instruction((operator as NOP).toJMP(),noOfExecutions)
    fun changeToNop() = Instruction((operator as JMP).toNOP(),noOfExecutions)
    fun cannotExecute() = noOfExecutions > 0
    fun reset(){noOfExecutions = 0}
}

fun String.parseIntoProgram(): Program = split("\n").map{it.split(" ")}.mapIndexed(::toInstruction).toMap()

fun toInstruction(index:Int, parts:List<String>):Pair<Int, Instruction> = when (parts[0]) {
    "jmp" -> Pair(index, Instruction(JMP(argument = parts[1].toInt())))
    "nop" -> Pair(index,Instruction(NOP(argument = parts[1].toInt())))
    "acc" -> Pair(index,Instruction(ACC(argument = parts[1].toInt())))
    else -> Pair(index,Instruction(NOP(argument = 0)))
}

fun Program.process( ):Pair<Int, Boolean> {
    var programState = ProgramState(0,0)
    while ( !(noMoreInstructions(programState) || isInLoop(programState)) ) {
        val instruction = getValue(programState.line)
        programState = instruction.execute(programState)
    }
    return Pair(programState.accumulator,noMoreInstructions(programState))
}

fun Program.noMoreInstructions(programState:ProgramState) = get(programState.line) == null
fun Program.isInLoop(programState:ProgramState) = getValue(programState.line).cannotExecute()
fun Program.reset() = forEach { _, v -> v.reset()}

fun Program.instructionsToSwap() = toList()
    .sortedBy{it.first}
    .filter{it.second.operator is JMP || it.second.operator is NOP}

fun Program.partTwo():Pair<Int, Boolean> {
    val instructionsToSwap = instructionsToSwap()
    var oneToSwap = 0
    while (oneToSwap < instructionsToSwap.size) {
        val (accumulator, finishedOK) = replaceInstruction(instructionsToSwap, oneToSwap++).process()
        if (finishedOK) return Pair(accumulator, finishedOK)
        reset()
    }
    return Pair(-1,false)
}

fun Program.replaceInstruction(instructionsToSwap: List<Pair<Int, Instruction>>, oneToSwap: Int):Program {
    val updatableProgram = toMutableMap()
    val swappableIndex = instructionsToSwap[oneToSwap].first
    val swappableInstruction = instructionsToSwap[oneToSwap].second
    updatableProgram[swappableIndex] = if (swappableInstruction.operator is JMP)
         updatableProgram.getValue(swappableIndex).changeToNop()
    else
         updatableProgram.getValue(swappableIndex).changeToJmp()
    return updatableProgram
}
