fun String.inRange(min:Int, max:Int):Boolean = toIntOrNull()?.let{ it in min..max } ?: false

// byr (Birth Year) - four digits; at least 1920 and at most 2002.
val validByr = {credential:String -> credential.inRange(1920,2002)    }
//iyr (Issue Year) - four digits; at least 2010 and at most 2020.
val validIyr = {credential:String -> credential.inRange(2010, 2020) }
//eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
val validEyr = {credential:String -> credential.inRange(2020, 2030)  }
//hgt (Height) - a number followed by either cm or in:
//If cm, the number must be at least 150 and at most 193.
//If in, the number must be at least 59 and at most 76.
val validHgt = {credential:String -> (credential.endsWith("cm") && credential.removeSuffix("cm").inRange(150, 193))
        || (credential.endsWith("in") && credential.removeSuffix("in").inRange(59, 76))
}
//hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
val validHcl = { credential:String ->
    credential.length == 7
            && credential.startsWith("#")
            && !credential.drop(1).map{"0123456789abcdef".contains(it)}.contains(false)
}
//ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
val validEcl = { credential:String -> listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(credential)}
//pid (Passport ID) - a nine-digit number, including leading zeroes.
val validPid = { credential:String ->  (credential.length == 9)  && (credential[0] != '-') && (credential.toLongOrNull() != null)}
//cid (Country ID) - ignored, missing or not.
val validCid = { credential:String -> true}
