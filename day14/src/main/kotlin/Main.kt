import kotlin.math.pow

data class Instruction(val address:Long, val value:Long) {
    fun applyMaskToValue(mask:String):Instruction = Instruction(address, value.applyMask(mask))

    fun updateMemory(memory:MutableMap<Long,Long>, mask:String) {
        memory[address] = applyMaskToValue(mask).value.toLong()
    }
    fun applyMaskToAddress(mask:String) =
        convertMask(mask).map{m ->
            Instruction(address.applyMask(m), value)
        }
    fun updateMemory2(memory:MutableMap<Long,Long>, mask:String) {
        val instructions = applyMaskToAddress(mask)
        instructions.forEach { memory[it.address] = it.value }
    }
}

fun Long.applyMask(mask: String): Long {
    val binary = toBinary()
    val zipped = mask.zip(binary)
    val maskedValue = zipped.map { p -> if (p.first != 'X') p.first else p.second }.joinToString("").binaryToLong()
    return maskedValue
}

data class Program(val mask:String, val instructions:List<Instruction> ) {
    fun process(memory:MutableMap<Long, Long>) {
        instructions.forEach { it.updateMemory(memory, mask) }
    }
    fun process2(memory:MutableMap<Long, Long>) {
        instructions.forEach { it.updateMemory2(memory, mask) }
    }

}

//This is horrible!
fun List<String>.parse():List<Program> {
    val chunks = mutableListOf<List<String>>()
    var chunk = mutableListOf<String>()
    forEach { line ->
        if (line.startsWith("mask")) {
            if (!chunk.isEmpty()) {
                chunks.add(chunk)
            }
            chunk = mutableListOf<String>(line)
        } else {
            chunk.add(line)
        }
    }
    if (!chunk.isEmpty()) {
        chunks.add(chunk)
        chunk = mutableListOf<String>()
    }
    val programs = chunks.map{
        val mask = it.first().split(" = ")[1]
        val instructions = it.drop(1).map{ line ->
                val splitLine = line.split(" = ")
                val value = splitLine[1].toLong()
                val address = splitLine[0].removePrefix("mem[").removeSuffix("]").toLong()
                Instruction(address, value)
        }
        Program(mask, instructions)
    }

    return programs
}

//binary conversion. This is horrible!

fun String.binaryToLong(chars:String = "1") :Long =
    reversed().mapIndexed{index, digit -> if ( chars.contains(digit)) pow(2, index) else 0 }.sum()
fun pow(a:Int, b:Int) = a.toDouble().pow(b.toDouble()).toLong()

fun Long.toBinary():String =
    (0..35).reversed().fold(Pair(this,"")){ (total, result), ndx ->
        val bin = 2.toDouble().pow(ndx).toLong()
        if ( (total / bin) >= 1L) Pair(total - bin, result + "1")
        else Pair(total, result + "0")
    }.second

//part two
fun formatMask(mask:String) = mask.replace('X','?').replace('0','X')
fun convertMask(mask:String) = convertQuestionMark( formatMask(mask))
fun convertQuestionMark(mask: String): List<String> {
    if (!mask.contains('?')) return listOf(mask)
    return listOf(mask.replaceFirst('?','0'),mask.replaceFirst('?','1'))
        .flatMap{convertQuestionMark(it)}
}

