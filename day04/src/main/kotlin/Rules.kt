fun String.inRange(min:Int, max:Int):Boolean = toIntOrNull()?.let{ it in min..max } ?: false


val byrRule = { credential:String -> credential.inRange(1920,2002)    }
val iyrRule = { credential:String -> credential.inRange(2010, 2020) }
val eyrRule = { credential:String -> credential.inRange(2020, 2030)  }
val hgtRule = { credential:String ->
           (credential.endsWith("cm") && credential.removeSuffix("cm").inRange(150, 193))
        || (credential.endsWith("in") && credential.removeSuffix("in").inRange(59, 76))
}
val hclRule = { credential:String ->
               credential.length == 7
            && credential.startsWith("#")
            && credential.drop(1).all{"0123456789abcdef".contains(it)}
}
val eclRule = { credential:String -> listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(credential)}
val pidRule = { credential:String ->  (credential.length == 9)  && (credential.all{"0123456789".contains(it)})}
val cidRule = { credential:String -> true}
