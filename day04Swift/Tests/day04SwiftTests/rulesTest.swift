import XCTest
@testable import day04Swift

final class rulesTests: XCTestCase {
    
    func testValidHgt() {
        XCTAssertTrue(hgtRule("150cm"))
        XCTAssertTrue(hgtRule("193cm"))
        XCTAssertFalse(hgtRule("149cm"))
        XCTAssertFalse(hgtRule("194cm"))
        XCTAssertTrue(hgtRule("59in"))
        XCTAssertTrue(hgtRule("76in"))
        XCTAssertFalse(hgtRule("58in"))
        XCTAssertFalse(hgtRule("77in"))
    }
    
    func testValidHairColours() {
        XCTAssertTrue(hclRule("#123456"))
        XCTAssertTrue(hclRule("#456789"))
        XCTAssertTrue(hclRule("#abcdef"))
        XCTAssertTrue(hclRule("#6b5442"))
        XCTAssertFalse(hclRule("##bcdef"))
        XCTAssertFalse(hclRule("#Abcdef"))
    }
    
    func testValidEyeColors() {
        let validColors = ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"]
        for validColor in validColors {
            XCTAssertTrue(eclRule(validColor))
        }
        XCTAssertFalse(eclRule("xyz"))
    }
    func testValidPid() {
        XCTAssertTrue(pidRule("012345678"))
        XCTAssertFalse(pidRule("01234567"))
        XCTAssertFalse(pidRule("0123x5679"))
        XCTAssertFalse(pidRule("0123.5679"))
        XCTAssertFalse(pidRule("-12345679"))
        XCTAssertFalse(pidRule("01234a5679"))
    }
    
}
