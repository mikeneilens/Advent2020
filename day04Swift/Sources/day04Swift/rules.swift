//
//  File.swift
//  
//
//  Created by Michael Neilens on 04/12/2020.
//
import Foundation

extension String {
    func inRange(_ range:ClosedRange<Int>) -> Bool {
        guard let value =  Int(self) else { return false}
        return range.contains(value)
    }
}

func byrRule(_ credential:String) -> Bool  { credential.inRange(1920...2002) }
func iyrRule(_ credential:String) -> Bool  { credential.inRange(2010...2020) }
func eyrRule(_ credential:String) -> Bool  { credential.inRange(2020...2030) }

func hgtRule(_ credential:String) -> Bool  {
    credential.hasSuffix("cm") && String(credential.dropLast(2)).inRange(150...193)
    || credential.hasSuffix("in") && String(credential.dropLast(2)).inRange(59...76)
}

func hclRule(_ credential:String) -> Bool  {
    return credential.count == 7 && credential.hasPrefix("#") && !credential.map{ "0123456789abcdef".contains($0)}.dropFirst().contains(false)
}

func eclRule(_ credential:String) -> Bool  {["amb", "blu", "brn", "gry", "grn", "hzl", "oth"].contains(credential)}
func pidRule(_ credential:String) -> Bool  {credential.count == 9 && !credential.map{ "0123456789".contains($0)}.contains(false) }
func cidRule(_ credential:String) -> Bool  {true}
