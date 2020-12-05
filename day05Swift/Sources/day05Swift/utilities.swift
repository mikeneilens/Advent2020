//
//  File.swift
//  
//
//  Created by Michael Neilens on 05/12/2020.
//

import Foundation

extension Decimal {
    var int: Int { NSDecimalNumber(decimal: self).intValue}
}


extension StringProtocol {
    func split(separator:String) -> Array<String> {
        reduce(Array<String>()){lines, char in
            var newLastLine = "\(lines.last ?? "")\(char)"
            if newLastLine.suffix(separator.count) == separator {
                newLastLine.removeLast(separator.count)
                return lines.dropLast() + [newLastLine,""]
            } else {
                return lines.dropLast() + [newLastLine]
            }
        }
    }
}
