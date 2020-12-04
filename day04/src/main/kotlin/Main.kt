val passportFields = mapOf(
    "byr" to byrRule,
    "iyr" to iyrRule,
    "eyr" to eyrRule,
    "hgt" to hgtRule,
    "hcl" to hclRule,
    "ecl" to eclRule,
    "pid" to pidRule,
    "cid" to cidRule,
)
val northPoleCredentialFields = passportFields.filter{it.key != "cid"}

typealias Passport = String
typealias Credentials = Map<String, String>

fun String.toPassports() = split("\n\n")

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