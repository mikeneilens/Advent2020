
data class ProgramState(val line:Int, val accumulator:Int)
typealias Program = Map<Int, Instruction>

sealed class Operator (val fn:(ProgramState, Int)->ProgramState, var argument:Int)
class NOP(fn:(ProgramState, Int)->ProgramState = {state, _ -> ProgramState(state.line + 1, state.accumulator )}, argument:Int):Operator(fn,argument)
class ACC(fn:(ProgramState, Int)->ProgramState = {state, arg -> ProgramState(state.line + 1, state.accumulator + arg)}, argument:Int):Operator(fn,argument)
class JMP(fn:(ProgramState, Int)->ProgramState = {state, arg -> ProgramState(state.line + arg, state.accumulator)}, argument:Int):Operator(fn,argument)

data class Instruction (val operator:Operator, private var noOfExecutions:Int = 0) {

    private fun operation(): (ProgramState) ->ProgramState = { programState -> noOfExecutions++;operator.fn(programState, operator.argument) }

    fun execute(programState:ProgramState):ProgramState = operation()(programState)

    fun cannotExecute() = noOfExecutions > 0
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
        programState = executeNextInstruction(programState)
    }
    return Pair(programState.accumulator,noMoreInstructions(programState))
}

fun Program.executeNextInstruction(programState: ProgramState) = getValue(programState.line).execute(programState)
fun Program.noMoreInstructions(programState:ProgramState) = get(programState.line) == null
fun Program.isInLoop(programState:ProgramState) = getValue(programState.line).cannotExecute()

fun part2(data:String):Pair<Int, Boolean> {
    var n = 0
    while ( n < data.split("\n").size ) {
        val program = data.replaceNthJmpOrNop(n++).parseIntoProgram()
        val (accumulator, finishedOK) = program.process()
        if (finishedOK) return Pair(accumulator, finishedOK)
    }
    return Pair(-1,false)
}

fun String.replaceNthJmpOrNop(n:Int):String {
    var count = 0
    return split("\n").map { s:String ->
        if ( s.startsWith("jmp") || s.startsWith("nop")) {
            if (++count == n) {
                if (s.startsWith("jmp")) "nop${s.drop(3)}" else "jmp${s.drop(3)}"
                } else  s
        } else s
    }.joinToString("\n")
}
