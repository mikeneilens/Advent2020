
typealias Memory = MutableMap<Long, Long>

data class Mask(val pattern:String) {
    val floatingMasks by lazy {                   //Make this lazy so that it isn't created for Part One data as that has too many Xs.
        pattern.replace('X','?') //This converts X into ? and 0 into X so mask can be applied in same way as part one.
        .replace('0','X')
        .floatingMasks()} //Then creates two masks that substitute a ? for a 1 and 0

    private fun String.floatingMasks(): List<Mask> {
        if (!contains('?')) return listOf(Mask(this))
        return listOf(replaceFirst('?','0'),replaceFirst('?','1')).flatMap{it.floatingMasks()}
    }
}

data class Instruction(val address:Long, val value:Long) {

    fun updateMemoryUsingValueMask(memory:Memory, mask:Mask) {
        memory[address] = applyMaskToValue(mask).value
    }

    fun applyMaskToValue(mask:Mask):Instruction = Instruction(address, value.applyMask(mask))

    fun updateMemoryUsingAddressMask(memory:Memory, mask:Mask) =
        applyMaskToAddress(mask).forEach { memory[it.address] = it.value }

    fun applyMaskToAddress(mask:Mask) = mask.floatingMasks.map(::transformAddress)

    private fun transformAddress(m:Mask) = Instruction(address.applyMask(m), value)
}

fun Long.applyMask(mask: Mask): Long {
    val zipped = mask.pattern.zip(toBinary()) //each digit in the mask is paired with each digit in the binary number
    return zipped.map (::applyMaskToADigit).joinToString("").bToLong()
}

fun applyMaskToADigit(p:Pair<Char, Char>) = if (p.first != 'X') p.first  else p.second

data class Program(val mask:Mask, val instructions:List<Instruction> ) {

    fun process(memory:Memory, memoryUpdater:Instruction.(Memory, Mask)->Unit)
            = instructions.forEach{it.memoryUpdater(memory, mask)}
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

fun String.toMask() = Mask(split(" = ")[1])

fun String.toInstruction():Instruction {
    val splitLine = split(" = ")
    val value = splitLine[1].toLong()
    val address = splitLine[0].removePrefix("mem[").removeSuffix("]").toLong()
    return Instruction(address, value)
}

fun partOne(data:List<String>):Map<Long, Long> {
    val memory = mutableMapOf<Long, Long>()
    data.parse().forEach{program ->  program.process(memory, Instruction::updateMemoryUsingValueMask)}
    return memory
}

//part two

fun partTwo(data:List<String>):Map<Long, Long> {
    val memory = mutableMapOf<Long, Long>()
    data.parse().forEach{program ->  program.process(memory, Instruction::updateMemoryUsingAddressMask)}
    return memory
}



