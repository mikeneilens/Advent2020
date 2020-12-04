
fun String.inRange(min:Int, max:Int):Boolean = toIntOrNull()?.let{ it in min..max } ?: false

val passportFields = mapOf(
    "byr" to validByr,
    "iyr" to validIyr,
    "eyr" to validEyr,
    "hgt" to validHgt,
    "hcl" to validHcl,
    "ecl" to validEcl,
    "pid" to validPid,
    "cid" to validCid,
)
val northPoleCredentialFields = passportFields.filter{it.key != "cid"}

typealias Passport = String
typealias Credentials = Map<String, String>

fun String.toPassports(passportSeparator:String = "\n\n") = split(passportSeparator)

fun Passport.toCredentials() =  replace("\n"," ").split(" ").map{ it.toKeyValuePair() }.toMap()

fun String.toKeyValuePair(keyValueSeparator:String = ":"):Pair<String, String> {
    val parts = split(keyValueSeparator)
    return Pair(parts[0],parts[1])
}

fun Credentials.passportFieldsArePresent() = keys.containsAll(passportFields.keys)

fun Credentials.northPoleCredentialsFieldsArePresent() = keys.containsAll(northPoleCredentialFields.keys)

fun String.passportsContainingRequiredFields():List<Credentials> = toPassports().map{it.toCredentials()}.filter{it.passportFieldsArePresent() || it.northPoleCredentialsFieldsArePresent()}

fun Credentials.allValid() =
    all { credential -> passportFields[credential.key]?.let { rule -> rule(credential.value) } ?: false }

fun String.validPassports() = passportsContainingRequiredFields().filter{it.allValid()}