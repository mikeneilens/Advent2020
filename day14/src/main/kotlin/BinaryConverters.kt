import kotlin.math.pow

//binary conversion. This is horrible!
typealias Binary = String

fun Binary.bToLong(chars:String = "1") :Long =
    reversed().mapIndexed{index, digit -> if ( chars.contains(digit)) pow(2, index) else 0 }.sum()

fun Long.toBinary():String =
    (0..35).reversed().fold(Pair(this,"")){ (total, result), ndx ->
        val bin = 2.0.pow(ndx).toLong()
        if ( (total / bin) >= 1L) Pair(total - bin, result + "1")
        else Pair(total, result + "0")
    }.second

fun pow(a:Int, b:Int) = a.toDouble().pow(b.toDouble()).toLong()