typealias Mask = String

data class Instruction(val address:Long, val value:Long) {

    fun updateMemoryUsingValueMask(memory:MutableMap<Long,Long>, mask:Mask) {
        memory[address] = applyMaskToValue(mask).value
    }

    fun applyMaskToValue(mask:Mask):Instruction = Instruction(address, value.applyMask(mask))

    fun updateMemoryUsingAddressMask(memory:MutableMap<Long,Long>, mask:Mask) =
        applyMaskToAddress(mask).forEach { memory[it.address] = it.value }

    fun applyMaskToAddress(mask:Mask) = mask.convert().map(::transformAddress)

    private fun transformAddress(m:Mask) = Instruction(address.applyMask(m), value)
}

fun Long.applyMask(mask: String): Long {
    val input = toBinary()
    val zipped = mask.zip(input)
    return zipped.map (::applyMaskToDigit).joinToString("").bToLong()
}

fun applyMaskToDigit(p:Pair<Char, Char>) = if (p.first != 'X') p.first  else p.second

data class Program(val mask:Mask, val instructions:List<Instruction> ) {

    fun processUsingValueMask(memory:MutableMap<Long, Long>) = instructions.forEach { it.updateMemoryUsingValueMask(memory, mask) }

    fun processUsingAddressMask(memory:MutableMap<Long, Long>) = instructions.forEach { it.updateMemoryUsingAddressMask(memory, mask) }
}

fun List<String>.parse():List<Program> {
    val listOfProgramData = joinToString("\n")
        .split("mask").drop(1)
        .map{it.removeSuffix("\n").split("\n")}
    return  listOfProgramData.map(::transformProgramData)
}

fun transformProgramData(programData:List<String>):Program {
    val mask = programData.first().toMask()
    val instructions = programData.drop(1).map (String::toInstruction)
    return Program(mask, instructions)
}

fun String.toMask() = split(" = ")[1]

fun String.toInstruction():Instruction {
    val splitLine = split(" = ")
    val value = splitLine[1].toLong()
    val address = splitLine[0].removePrefix("mem[").removeSuffix("]").toLong()
    return Instruction(address, value)
}

//part two
fun Mask.convert() =  replace('X','?')
                                .replace('0','X')
                                .floatingMasks()

fun Mask.floatingMasks(): List<String> {
    if (!contains('?')) return listOf(this)
    return listOf(replaceFirst('?','0'),replaceFirst('?','1')).flatMap{it.floatingMasks()}
}

