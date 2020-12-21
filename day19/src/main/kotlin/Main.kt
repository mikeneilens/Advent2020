
fun List<String>.encloseNumbers():List<String> {
    var data = this
    (0..200).reversed().forEach{
        data = data.map{line -> line.replace(" $it "," ($it) ") }
        data = data.map{line -> line.replace(" $it"," ($it)") }
        data = data.map{line -> line.replace("$it:","($it):") }
    }
    return data.map{it.replace(" ","")}
}


fun String.product(other:String):String {
    val thisKey = split(":")[0]
    val otherKey = other.split(":")[0]
    val otherParts  = other.split(":")[1].split("|").distinct()
    val thisParts = split(":")[1].split("|").distinct()

    if ( !thisParts.any{ it.contains(otherKey)} ) return this

    var output = "$thisKey:"
    thisParts.forEach{thisPart ->
        otherParts.forEach { otherPart ->
            output = output + thisPart.replace(otherKey, otherPart)  + "|"
        }
    }
    return output.removeSuffix("|")
}

fun String.onlyLetters() = split(":")[1].none { it == '('}

fun List<String>.loop():List<String> {
    val lineContainingLetters = firstOrNull(String::onlyLetters) ?: return this
    val result = mutableListOf<String>()
    forEach{line ->
        if (line != lineContainingLetters) {
            result += line.product(lineContainingLetters)
        }
    }
    println("looped $size")
    if(size == 3) {
        println("nearly there")
    }
    return result
}

fun List<String>.process():List<String>{
    return if (size == 1) this
    else loop().process()
}