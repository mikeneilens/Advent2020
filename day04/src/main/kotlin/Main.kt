
fun String.inRange(min:Int, max:Int):Boolean = toIntOrNull()?.let{ it >= min && it <= max } ?: false

val passportfields = mapOf(
    "byr" to validByr,
    "iyr" to validIyr,
    "eyr" to validEyr,
    "hgt" to validHgt,
    "hcl" to validHcl,
    "ecl" to validEcl,
    "pid" to validPid,
    "cid" to validCid,
)
val northPoleCredentialFields = passportfields.filter{it.key != "cid"}

typealias Passport = String
typealias Credentials = Map<String, String>

const val passportSeperator = "\n\n"

fun String.toPassports() = split(passportSeperator)

fun Passport.toCredentials() =  replace("\n"," ").split(" ").map{ it.toKeyValuePair() }.toMap()

fun String.toKeyValuePair(keyValueSeperator:String = ":"):Pair<String, String> {
    val parts = split(keyValueSeperator)
    return Pair(parts[0],parts[1])
}

fun Credentials.passportFieldsArePresent() = keys.containsAll(passportfields.keys)

fun Credentials.northPoleCredentialsFieldsArePresent() = keys.containsAll(northPoleCredentialFields.keys)

fun String.passportsContainingRequiredFields():List<Credentials> = toPassports().map{it.toCredentials()}.filter{it.passportFieldsArePresent() || it.northPoleCredentialsFieldsArePresent()}

fun Credentials.allValid() =
    map{credential -> passportfields[credential.key]?.let{ rule -> rule(credential.value)}}.filter { it == false }.size == 0

fun String.validPassports() = passportsContainingRequiredFields().map{it.allValid()}.filter{it}