//
//  File.swift
//  
//
//  Created by Michael Neilens on 04/12/2020.
//

import Foundation

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
    func replace(_ old:Character, _ new:Character) -> String {
        map{ ($0 == old) ? String(new) : String($0)}.joined()
    }
}

extension Array where Element == (String, String) {
    func toDictionary() -> Dictionary<String, String> {
        var dictionary = Dictionary<String, String>()
        forEach{ (key,value) in
            dictionary[key] = value
        }
        return dictionary
    }
}
