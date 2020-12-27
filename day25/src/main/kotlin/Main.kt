
fun transform(value:Long, subjectNumber:Long):Long {
    return (value * subjectNumber) % 20201227L
}

fun transformUntil(key:Long):Long {
    var value = 1L
    var noOfLoops = 0L
    while (value != key) {
        value = transform(value,7L)
        noOfLoops++
    }
    return noOfLoops
}

fun calculateEncryptionKey(subjectNumber:Long, times:Long):Long {
    var value = 1L
    (1..times).forEach{
        value = transform(value,subjectNumber)
    }
    return value
}

fun partOne(cardKey:Long, doorKey:Long):Long {
    val cardLoops = transformUntil(cardKey)
    val doorLoops = transformUntil(doorKey)

    return calculateEncryptionKey(doorKey, cardLoops.toLong())
}