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

fun toCredentials(passport:Passport) = passport.replace("\n"," ")
                                                                .split(" ")
                                                                .map(::toCredential).toMap()

fun toCredential(s:String, keyValueSeparator:String = ":"):Pair<String, String> {
    val parts = s.split(keyValueSeparator)
    return Pair(parts[0],parts[1])
}

fun Credentials.passportFieldsArePresent() = keys.containsAll(passportFields.keys)
fun Credentials.northPoleCredentialsFieldsArePresent() = keys.containsAll(northPoleCredentialFields.keys)
fun passportOrNortPoleCredentialsArePresent(credentials:Credentials) = credentials.passportFieldsArePresent() || credentials.northPoleCredentialsFieldsArePresent()

fun String.passportsContainingRequiredFields():List<Credentials> = toPassports()
                                                                    .map(::toCredentials)
                                                                    .filter(::passportOrNortPoleCredentialsArePresent)

fun allValid(credentials:Credentials) =
    credentials.all { credential -> passportFields[credential.key]?.let { rule -> rule(credential.value) } ?: false }

fun String.validPassports() = passportsContainingRequiredFields().filter(::allValid)