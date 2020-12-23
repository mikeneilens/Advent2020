

class Cup{
    let label:Int
    var next:Cup?
    init(_ label:Int, _ next:Cup? = nil ) {
        self.label = label
        self.next = next
    }
    
    func toListOfInts() -> Array<Int> {
        var list = [label]
        var nextCup = next
        while (nextCup !== self && nextCup != nil) {
            list.append(nextCup!.label)
            nextCup = nextCup!.next
        }
        return list
    }

    func pickupThreeCups() -> Cup {
        let cup1 = next
        let cup2 = cup1?.next
        let cup3 = cup2?.next
        self.next = cup3?.next
        cup3?.next = nil
        return cup1!
    }
    
    func contains(_ otherLabel:Int, _ firstCup:Cup? = nil) ->Bool {
        if (label == otherLabel ) {return true}
        if (label == firstCup?.label) {return false}
        if ( next == nil) {return false}
        return next!.contains(otherLabel, firstCup ?? self )
    }

    func getDestinationCup(_ threeCups:Cup, _ maxSize:Int = 9) -> Cup {
        let destinationLabel = getDestinationLabel(threeCups, maxSize)
        let cupInMap = cupsForLabels[destinationLabel]
        return cupInMap!
    }
    
    func getDestinationLabel(_ threeCups: Cup, _ maxSize: Int) -> Int {
        if (!threeCups.contains(label.labelMinus(1, maxSize))) {
             return label.labelMinus(1, maxSize)}
        else if (!threeCups.contains(label.labelMinus(2, maxSize))) {
            return label.labelMinus(2, maxSize) }
        else if (!threeCups.contains(label.labelMinus(3, maxSize))) {
            return label.labelMinus(3, maxSize) }
        else {
            return label.labelMinus(4, maxSize) }
    }
    
    func playRound(_ maxSize:Int = 9) -> Cup {
        let threeCups = pickupThreeCups()
        let destinationCup = getDestinationCup(threeCups, maxSize)
        threeCups.next!.next!.next = destinationCup.next
        destinationCup.next = threeCups
        return self.next!
    }

}

func process(_ data:Array<Int>, _ times:Int = 10)-> Cup {
    let maxSize = data.max()  //maxByOrNull { it } ?: 9
    var cup = data.toCup()
    (1...times).forEach{ _ in
        cup = cup.playRound(maxSize!)
    }
    return cup
}

var cupsForLabels = Dictionary<Int, Cup>()

extension Array where Element == Int {
    func toCup()-> Cup {
        
        let firstCup = Cup(first ?? 0)
        cupsForLabels[first ?? 0] = firstCup
        var prevCup:Cup? = firstCup
       
        dropFirst(1).forEach{ it in
            let nextCup = Cup(it)
            cupsForLabels[it] = nextCup
            prevCup?.next = nextCup
            prevCup = nextCup
        }
        prevCup?.next = firstCup
        return firstCup
    }
}

extension Int {
    func labelMinus(_ value:Int, _ maxSize:Int) -> Int {
        let newLabel = self - value
        return (newLabel > 0) ? newLabel : maxSize + newLabel
    }
}
